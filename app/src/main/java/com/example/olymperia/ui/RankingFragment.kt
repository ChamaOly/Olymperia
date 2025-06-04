package com.example.olymperia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentRankingBinding
import com.example.olymperia.model.RankingUser
import com.example.olymperia.network.StravaApiService
import com.example.olymperia.ui.adapters.RankingAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import com.example.olymperia.utils.ScoreManager
import kotlinx.coroutines.tasks.await



class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private lateinit var adapter: RankingAdapter
    private val prefs by lazy { requireContext().getSharedPreferences("strava_prefs", android.content.Context.MODE_PRIVATE) }
    private val api by lazy { StravaApiService.create() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRankingDeAmigos()
    }

    private fun loadRankingDeAmigos() {
        val token = prefs.getString("access_token", null) ?: return
        val currentAthleteId = prefs.getLong("athlete_id", -1L)
        if (currentAthleteId == -1L) return

        val usuariosRanking = mutableListOf<RankingUser>()

        // Usuario actual primero
        val total = ScoreManager.getTotalPoints(requireContext())
        val nivel = maxOf(1, total / 200)
        usuariosRanking.add(RankingUser(currentAthleteId, "Tú", nivel, total))

        lifecycleScope.launch {
            try {
                val amigos = api.getAthleteFriends("Bearer $token")
                val dbRef = FirebaseDatabase.getInstance("https://olymperia-default-rtdb.europe-west1.firebasedatabase.app").getReference("usuarios")

                amigos.forEach { amigo ->
                    val id = amigo.id.toString()
                    try {
                        val snapshot = dbRef.child(id).get().await()
                        if (snapshot.exists()) {
                            val nombre = snapshot.child("nombre").value as? String ?: return@forEach
                            val nivelAmigo = (snapshot.child("nivel").value as? Long)?.toInt() ?: return@forEach
                            val puntos = (snapshot.child("puntos").value as? Long)?.toInt() ?: return@forEach

                            usuariosRanking.add(RankingUser(amigo.id, nombre, nivelAmigo, puntos))
                        }
                    } catch (e: Exception) {
                        // Saltar si hay error con este amigo
                    }
                }

                // Mantener usuario actual primero, ordenar resto
                val encabezado = usuariosRanking.first()
                val amigosOrdenados = usuariosRanking.drop(1).sortedByDescending { it.nivel }
                val rankingFinal = mutableListOf(encabezado).apply { addAll(amigosOrdenados) }

                adapter = RankingAdapter(rankingFinal)
                binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
                binding.rvRanking.adapter = adapter

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar ranking de amigos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
