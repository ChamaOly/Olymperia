package com.example.olymperia.ui

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.TextView
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
import com.example.olymperia.R

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
        binding.tvProvinceName.text = "Provincia: $province"
        cargarPuertos()
    }

    private fun cargarPuertos() {
        val basePorts = PortRepository.getSegmentsByProvince(province)
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

                val mensaje = ScoreManager.procesarEsfuerzosStrava(
                    requireContext(),
                    port.id,
                    completedCount,
                    port.points
                )

                if (mensaje != null) {
                    val total = ScoreManager.getTotalPoints(requireContext())
                    val textoFinal = "$mensaje\nTotal actual: $total"

                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.dialog_points_earned)
                    dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    dialog.window?.setGravity(Gravity.CENTER)

                    val tv = dialog.findViewById<TextView>(R.id.tvPointsMessage)
                    tv.text = textoFinal

                    val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.honor_unlocked)
                    mediaPlayer?.apply {
                        setOnCompletionListener { player -> player.release() }
                        start()
                    }

                    dialog.show()
                } else {
                    Toast.makeText(requireContext(), "Subido $completedCount veces — sin puntos nuevos", Toast.LENGTH_SHORT).show()
                }

                cargarPuertos()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al consultar Strava", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
