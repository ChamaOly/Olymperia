package com.example.olymperia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentPortListBinding
import com.example.olymperia.model.PortSegment
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.PortAdapter
import com.example.olymperia.ui.HonorUnlockedDialog
import com.example.olymperia.utils.ScoreManager
import com.example.olymperia.network.StravaApi
import kotlinx.coroutines.launch

class PortListFragment : Fragment() {

    private var _binding: FragmentPortListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PortAdapter
    private lateinit var ports: List<PortSegment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val provincias = arguments?.getStringArray("provincias") ?: emptyArray()
        ports = PortRepository.getAllSegments().filter { it.province in provincias }

        adapter = PortAdapter(ports, { port ->
            val prefs = requireContext().getSharedPreferences("strava_prefs", 0)
            val token = prefs.getString("access_token", null)
            val athleteId = prefs.getLong("athlete_id", -1L)

            if (!token.isNullOrEmpty() && athleteId != -1L) {
                val strava = StravaApi.create(token)

                lifecycleScope.launch {
                    try {
                        val efforts = strava.getSegmentEfforts(
                            auth = "Bearer $token",
                            segmentId = port.id,
                            athleteId = athleteId
                        )

                        Log.d("EFFORTS_DEBUG", "Segment ${port.id} efforts = ${efforts.size}")

                        val resultado = ScoreManager.procesarEsfuerzosStrava(
                            requireContext(),
                            port.id,
                            efforts.size,
                            port.points
                        ) { honor ->
                            Toast.makeText(requireContext(), "🏆 Desbloqueado: ${honor.nombre}", Toast.LENGTH_LONG).show()
                            HonorUnlockedDialog(honor) {
                                Log.d("HONOR", "Honor desbloqueado: ${honor.nombre}")
                            }.show(parentFragmentManager, "honor_dialog")
                        }

                        if (!resultado.isNullOrEmpty()) {
                            binding.tvResultadoPuntos.text = "🏆 $resultado"
                        } else {
                            binding.tvResultadoPuntos.text = "✅ Ya has registrado todos los esfuerzos de Strava."
                        }

                        binding.tvResultadoPuntos.visibility = View.VISIBLE
                        adapter.notifyItemChanged(ports.indexOf(port))

                    } catch (e: Exception) {
                        Log.e("STRAVA_ERROR", "Error consultando esfuerzos", e)
                        Toast.makeText(requireContext(), "Error al consultar Strava", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Falta acceso válido a Strava", Toast.LENGTH_SHORT).show()
            }
        }, requireContext())

        binding.recyclerProvinces.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProvinces.adapter = adapter

        binding.tvResultadoPuntos.setOnClickListener {
            it.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}