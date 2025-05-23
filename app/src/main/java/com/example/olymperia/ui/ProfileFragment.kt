package com.example.olymperia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.R
import com.example.olymperia.databinding.FragmentProfileBinding
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.ui.Adapters.HighlightedTrophyAdapter
import com.example.olymperia.utils.ScoreManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("strava_prefs", 0)

        try {
            val total = ScoreManager.getTotalPoints(requireContext())
            val level = maxOf(1, total / 200)

            binding.tvLevel.text = "Nivel $level – $total puntos"
            binding.tvDivisionTexto.text = "División $level"

            val fondo = when (level) {
                in 1..3 -> R.drawable.bg_division_oro
                in 4..6 -> R.drawable.bg_division_plata
                else -> R.drawable.bg_division_cobre
            }
            binding.tvDivisionTexto.setBackgroundResource(fondo)

            val categorias = contarCategoriasCompletadas()
            binding.tvHC.text = "HC: ${categorias["HC"] ?: 0}"
            binding.tv1.text = "1: ${categorias["1"] ?: 0}"
            binding.tv2.text = "2: ${categorias["2"] ?: 0}"
            binding.tv3.text = "3: ${categorias["3"] ?: 0}"

        } catch (e: Exception) {
            binding.tvAthleteName.text = "Error al cargar perfil"
            Log.e("PROFILE_ERROR", "Error general", e)
        }

        val highlighted = listOf("Medalla Oro", "Título Honorífico", "Trofeo de Montaña")
        binding.rvHighlightedTrophies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHighlightedTrophies.adapter = HighlightedTrophyAdapter(highlighted)

        binding.btnResetUser.setOnClickListener {
            val editor = prefs.edit()
            val provincias = PortRepository.getAllSegments().map { it.province }.distinct()
            provincias.forEach {
                val clave = it.lowercase().replace(" ", "_")
                editor.remove("conquistador_$clave")
                editor.remove("rey_$clave")
            }
            editor.clear().apply()

            Toast.makeText(requireContext(), "Usuario reiniciado", Toast.LENGTH_SHORT).show()
            binding.tvAthleteName.text = ""
            binding.tvLevel.text = "Nivel 0"
            binding.imgAvatar.setImageResource(R.drawable.ic_user)
        }
    }

    private fun actualizarNivel() {
        val total = ScoreManager.getTotalPoints(requireContext())
        val level = maxOf(1, total / 200)

        binding.tvLevel.text = "Nivel $level – $total puntos"
        binding.tvDivisionTexto.text = "División $level"

        val fondo = when (level) {
            in 1..3 -> R.drawable.bg_division_oro
            in 4..6 -> R.drawable.bg_division_plata
            else -> R.drawable.bg_division_cobre
        }
        binding.tvDivisionTexto.setBackgroundResource(fondo)

        val categorias = contarCategoriasCompletadas()
        binding.tvHC.text = "HC: ${categorias["HC"] ?: 0}"
        binding.tv1.text = "1: ${categorias["1"] ?: 0}"
        binding.tv2.text = "2: ${categorias["2"] ?: 0}"
        binding.tv3.text = "3: ${categorias["3"] ?: 0}"
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null && isAdded) {
            actualizarNivel()
        }
    }

    private fun contarCategoriasCompletadas(): Map<String, Int> {
        val segments = PortRepository.getAllSegments()
        val uniqueByCategory = mutableMapOf<String, MutableSet<Long>>()

        segments.forEach { segment ->
            val completed = ScoreManager.getCompletedCount(requireContext(), segment.id)
            if (completed >= 1) {
                val categoria = when {
                    segment.points >= 251 -> "HC"
                    segment.points in 171..250 -> "1"
                    segment.points in 100..170 -> "2"
                    else -> "3"
                }
                uniqueByCategory.getOrPut(categoria) { mutableSetOf() }.add(segment.id)
            }
        }

        return uniqueByCategory.mapValues { it.value.size }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
