/// MainActivity.kt limpio y funcional con sincronización de Firebase
package com.example.olymperia

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.olymperia.databinding.ActivityMainBinding
import com.example.olymperia.model.Athlete
import com.example.olymperia.model.SegmentEffort
import com.example.olymperia.network.StravaApiService
import com.example.olymperia.utils.UserProgressManager
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val prefs by lazy { getSharedPreferences("strava_prefs", MODE_PRIVATE) }
    private val api by lazy { StravaApiService.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        ensureFirebaseAuth()
        loadAthlete()
    }

    override fun onResume() {
        super.onResume()
        loadAthlete()
    }

    private fun ensureFirebaseAuth() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            auth.signInAnonymously()
                .addOnSuccessListener {
                    Log.d("FIREBASE_AUTH", "Autenticado anónimamente en Firebase")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE_AUTH", "Fallo en login anónimo", it)
                }
        }
    }

    private fun loadAthlete() {
        prefs.getString("access_token", null)?.let { token ->
            lifecycleScope.launch {
                try {
                    val athlete: Athlete = api.getAthlete("Bearer $token")
                    prefs.edit().putLong("athlete_id", athlete.id).apply()

                    val progress = UserProgressManager.loadProgress(applicationContext)

                    val usuario = mapOf(
                        "id" to athlete.id,
                        "nombre" to "${athlete.firstname} ${athlete.lastname}",
                        "nivel" to maxOf(1, progress.totalPoints / 200),
                        "puntos" to progress.totalPoints,
                        "puertosCompletados" to progress.completedSegments.toList()
                    )

                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                    val db = FirebaseDatabase.getInstance("https://olymperia-default-rtdb.europe-west1.firebasedatabase.app")
                    db.getReference("usuarios")
                        .child(uid)
                        .setValue(usuario)

                    Log.d("FIREBASE_WRITE", "Usuario actualizado en Firebase con UID $uid")

                } catch (e: Exception) {
                    Log.e("FIREBASE_WRITE", "Error al cargar atleta desde Strava", e)
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
                // Aquí podrías registrar esfuerzos si lo necesitas
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar esfuerzos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
