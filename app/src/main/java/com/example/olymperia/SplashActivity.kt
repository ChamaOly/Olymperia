package com.example.olymperia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)

            val prefs = getSharedPreferences("strava_prefs", MODE_PRIVATE)
            val token = prefs.getString("access_token", null)
            val expiresAt = prefs.getLong("expires_at", 0)
            val isStillValid = System.currentTimeMillis() / 1000 < expiresAt

            if (token != null && isStillValid) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }
}
