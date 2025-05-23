package com.example.olymperia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.olymperia.model.Segment
import com.example.olymperia.network.StravaApiService
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.strava.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val token = getSharedPreferences("strava_prefs", MODE_PRIVATE)
                            .getString("access_token", "")!!
                        chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                            .let(chain::proceed)
                    }
                    .build()
            )
            .build()
            .create(StravaApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val fragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        fragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isScrollGesturesEnabled = true

        googleMap.setOnPolylineClickListener { polyline ->
            val segment = (polyline.tag as? Segment) ?: return@setOnPolylineClickListener
            val segmentId = segment.id
            val athleteId = getSharedPreferences("strava_prefs", MODE_PRIVATE)
                .getLong("athlete_id", 0L)

            lifecycleScope.launch {
                try {
                    val token = getSharedPreferences("strava_prefs", MODE_PRIVATE)
                        .getString("access_token", "")!!
                    val efforts = apiService.getSegmentEfforts(
                        auth = "Bearer $token",
                        segmentId = segmentId,
                        athleteId = athleteId
                    )
                    Toast.makeText(
                        this@MapActivity,
                        "Has completado este segmento ${efforts.size} veces",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@MapActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
