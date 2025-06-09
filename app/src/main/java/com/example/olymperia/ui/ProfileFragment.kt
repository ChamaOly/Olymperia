package com.example.olymperia.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.olymperia.R
import com.example.olymperia.databinding.FragmentProfileBinding
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.ui.Adapters.HighlightedTrophyAdapter
import com.example.olymperia.utils.ScoreManager
import com.example.olymperia.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import resetYReconstruirLogros

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("strava_prefs", 0)
        val athleteId = prefs.getLong("athlete_id", -1L)

        if (athleteId != -1L) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(athleteId.toString())
                .get()
                .addOnSuccessListener { document ->
                    val total = (document.getLong("totalPoints") ?: 0).toInt()
                    val level = maxOf(1, total / 200)
                    val division = getDivision(level)

                    binding.tvLevel.text = "Nivel $level – $total puntos"
                    binding.tvDivisionTexto.text = "División $division"

                    val fondo = when (division) {
                        1 -> R.drawable.ic_division1
                        2 -> R.drawable.ic_division2
                        3 -> R.drawable.ic_division3
                        4 -> R.drawable.ic_division4
                        5 -> R.drawable.ic_division5
                        6 -> R.drawable.ic_division6
                        7 -> R.drawable.ic_division7
                        8 -> R.drawable.ic_division8
                        9 -> R.drawable.ic_division9
                        10 -> R.drawable.ic_division10
                        else -> R.drawable.ic_division10
                    }
                    binding.tvDivisionTexto.setBackgroundResource(fondo)

                    val nombre = document.getString("name") ?: prefs.getString("athlete_name", "Atleta")
                    val avatar = document.getString("avatarUrl") ?: prefs.getString("avatar_url", null)
                    binding.tvAthleteName.text = nombre

                    if (avatar != null) {
                        Glide.with(binding.imgAvatar.context)
                            .load(avatar)
                            .placeholder(R.drawable.ic_user)
                            .circleCrop()
                            .into(binding.imgAvatar)
                    } else {
                        binding.imgAvatar.setImageResource(R.drawable.ic_user)
                    }

                    val categorias = contarCategoriasCompletadas()
                    binding.tvHC.text = "${categorias["HC"] ?: 0}"
                    binding.tv1.text = "${categorias["1"] ?: 0}"
                    binding.tv2.text = "${categorias["2"] ?: 0}"
                    binding.tv3.text = "${categorias["3"] ?: 0}"
                }
                .addOnFailureListener {
                    Log.e("PROFILE_ERROR", "Error al cargar datos desde Firebase", it)
                    binding.tvLevel.text = "Nivel ? – error"
                }
        }

        val highlighted = listOf("Medalla Oro", "Título Honorífico", "Trofeo de Montaña")
        binding.rvHighlightedTrophies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHighlightedTrophies.adapter = HighlightedTrophyAdapter(highlighted)

        binding.btnResetUser.setOnClickListener {
            // 🧠 Resetear logros y contadores locales
            resetYReconstruirLogros(requireContext())

            // ✍️ Guardar nombre/avatar temporal antes de borrar prefs
            val prefs = requireContext().getSharedPreferences("strava_prefs", 0)
            val nombre = prefs.getString("athlete_name", null)
            val avatar = prefs.getString("avatar_url", null)

            // 🧹 Limpiar preferencias locales
            val editor = prefs.edit()
            editor.clear()
            if (nombre != null) editor.putString("athlete_name", nombre)
            if (avatar != null) editor.putString("avatar_url", avatar)
            editor.apply()

            // 🔐 Cerrar sesión Firebase anónima
            FirebaseAuth.getInstance().signOut()

            // 🔁 Lanzar login otra vez
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }




        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDivision(level: Int): Int {
        return when {
            level in 0..10 -> 10
            level in 11..20 -> 9
            level in 21..30 -> 8
            level in 31..40 -> 7
            level in 41..50 -> 6
            level in 51..60 -> 5
            level in 61..70 -> 4
            level in 71..80 -> 3
            level in 81..90 -> 2
            level in 91..100 -> 1
            else -> 10
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
