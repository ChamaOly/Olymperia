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
import com.example.olymperia.databinding.FragmentRankingBinding
import com.example.olymperia.model.RankingUser
import com.example.olymperia.ui.adapters.RankingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private lateinit var adapter: RankingAdapter
    private val prefs by lazy {
        requireContext().getSharedPreferences("strava_prefs", android.content.Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarUsuarioActual()
        loadRankingDeUsuarios()
    }

    override fun onResume() {
        super.onResume()
        cargarUsuarioActual()
        loadRankingDeUsuarios()
    }

    private fun cargarUsuarioActual() {
        val currentAthleteId = prefs.getLong("athlete_id", -1L)
        if (currentAthleteId == -1L) return

        lifecycleScope.launch {
            try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentAthleteId.toString())
                    .get()
                    .await()

                if (snapshot.exists()) {
                    val level = (snapshot.getLong("level") ?: 1L).toInt()
                    val points = (snapshot.getLong("totalPoints") ?: 0L).toInt()

                    binding.tvNivel.text = "Nivel $level"
                    binding.tvDivision.text = "División ${getDivision(level)}"
                    binding.tvPuntos.text = "$points puntos"
                } else {
                    binding.tvNivel.text = "Nivel ?"
                    binding.tvDivision.text = "División ?"
                    binding.tvPuntos.text = "? puntos"
                }
            } catch (e: Exception) {
                Log.e("RANKING_USER", "Error al cargar usuario actual", e)
            }
        }
    }

    private fun loadRankingDeUsuarios() {
        lifecycleScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                val snapshot = db.collection("users").get().await()
                val usuariosRanking = mutableListOf<RankingUser>()
                val currentAthleteId = prefs.getLong("athlete_id", -1L)

                for (doc in snapshot.documents) {
                    val athleteId = doc.getLong("athleteId") ?: continue
                    if (athleteId == currentAthleteId) continue

                    val name = doc.getString("name")?.takeIf { it.isNotBlank() } ?: continue
                    val level = (doc.getLong("level") ?: 1L).toInt()
                    val points = (doc.getLong("totalPoints") ?: 0L).toInt()

                    usuariosRanking.add(RankingUser(athleteId, name, level, points))
                }

                val ordenados = usuariosRanking.sortedByDescending { it.nivel }
                adapter = RankingAdapter(ordenados)
                binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
                binding.rvRanking.adapter = adapter

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar ranking", Toast.LENGTH_SHORT).show()
                Log.e("RANKING_ERROR", "Fallo al cargar usuarios desde Firestore", e)
            }
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
}
