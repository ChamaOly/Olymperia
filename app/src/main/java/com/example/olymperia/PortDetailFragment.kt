package com.example.olymperia.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentPortDetailBinding
import com.example.olymperia.model.PortSegment
import com.example.olymperia.network.StravaApi
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.utils.ScoreManager
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar

class PortDetailFragment : Fragment() {

    private lateinit var binding: FragmentPortDetailBinding
    private lateinit var adapter: PortSegmentAdapter
    private lateinit var province: String
    private lateinit var enrichedPorts: List<PortSegment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        province = arguments?.getString("province") ?: return
        cargarPuertos()
    }

    private fun cargarPuertos() {
        val basePorts = PortRepository.getSegmentsByProvince(province)

        // Añadir completedCount
        enrichedPorts = basePorts.map { port ->
            val count = ScoreManager.getCompletedCount(requireContext(), port.id)
            port.copy(completedCount = count)
        }

        adapter = PortSegmentAdapter { port -> checkStravaEfforts(port) }

        binding.rvPorts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPorts.adapter = adapter
        adapter.submitList(enrichedPorts)
    }

    private fun checkStravaEfforts(port: PortSegment) {
        val prefs = requireActivity().getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
        val token = prefs.getString("access_token", null)
        val athleteId = prefs.getLong("athlete_id", 0)

        if (token == null || athleteId == 0L) {
            Toast.makeText(requireContext(), "Strava no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        val api = StravaApi.create(token)

        lifecycleScope.launch {
            try {
                val efforts = api.getSegmentEfforts("Bearer $token", port.id, athleteId)
                val completedCount = efforts.size

                // Guardar repeticiones
                val prefs = requireContext().getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
                prefs.edit().putInt("completed_count_${port.id}", completedCount).apply()

                // Añadir puntos si toca
                val mensaje = ScoreManager.addPointsIfEligible(requireContext(), port.id, completedCount, port.points)

                if (mensaje != null) {
                    val total = ScoreManager.getTotalPoints(requireContext())
                    val textoFinal = "$mensaje\nTotal actual: $total"

                    // Mostrar en pantalla
                    binding.tvResultadoPuntos.text = textoFinal
                    binding.tvResultadoPuntos.visibility = View.VISIBLE

                    // También mostrar como Toast
                    Snackbar.make(requireView(), textoFinal, Snackbar.LENGTH_LONG).show()

                    // Recargar lista visual si quieres
                    cargarPuertos()



                } else {
                    Snackbar.make(requireView(), "Subido $completedCount veces — sin puntos nuevos", Snackbar.LENGTH_SHORT).show()
                }

                // 🔁 Recargar puertos para actualizar insignias
                cargarPuertos()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al consultar Strava", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
