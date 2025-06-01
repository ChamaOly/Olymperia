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

        // Configura la navegación inferior
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun loadAthlete() {
        prefs.getString("access_token", null)?.let { token ->
            lifecycleScope.launch {
                try {
                    val athlete: Athlete = api.getAthlete("Bearer $token")
                    prefs.edit().putLong("athlete_id", athlete.id).apply()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error al cargar atleta", Toast.LENGTH_SHORT)
                        .show()
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
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar esfuerzos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
