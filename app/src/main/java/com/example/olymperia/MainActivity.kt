package com.example.olymperia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.olymperia.databinding.ActivityMainBinding
import com.example.olymperia.model.Athlete
import com.example.olymperia.model.SegmentEffort
import com.example.olymperia.network.StravaApiService
import kotlinx.coroutines.launch
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val prefs by lazy { getSharedPreferences("strava_prefs", MODE_PRIVATE) }
    private val api by lazy { StravaApiService.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura navegación inferior
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNav.setupWithNavController(navController)

        // Carga datos del atleta si se requiere en el futuro desde aquí (actualmente lo hace el fragmento)
        loadAthlete()
    }

    private fun loadAthlete() {
        prefs.getString("access_token", null)?.let { token ->
            lifecycleScope.launch {
                try {
                    val athlete: Athlete = api.getAthlete("Bearer $token")

                    // Aquí podrías guardar en prefs, si el fragmento lo necesita
                    prefs.edit().putLong("athlete_id", athlete.id).apply()

                    // Ya no modificamos vistas aquí, eso lo hace ProfileFragment

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error al cargar atleta", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: Toast.makeText(this, "Token no disponible", Toast.LENGTH_SHORT).show()
    }

    private fun loadEfforts(segmentId: Long) {
        val token = prefs.getString("access_token", null)
        val athleteId = prefs.getLong("athlete_id", 0L)
        if (token == null || athleteId == 0L) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val efforts: List<SegmentEffort> =
                    api.getSegmentEfforts("Bearer $token", segmentId, athleteId)

                // Aquí iría el adaptador si rvEfforts se usa desde fragmento o desde otra vista
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar esfuerzos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
