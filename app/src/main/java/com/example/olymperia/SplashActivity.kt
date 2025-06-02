package com.example.olymperia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import android.media.MediaPlayer


class SplashActivity : AppCompatActivity() {
    private var splashPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashPlayer = MediaPlayer.create(this, R.raw.splash_sound)
        splashPlayer?.isLooping = false
        splashPlayer?.start()




        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)

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
    override fun onDestroy() {
        super.onDestroy()
        splashPlayer?.release()
        splashPlayer = null
    }

}
