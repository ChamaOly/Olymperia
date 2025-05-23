package com.example.olymperia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.olymperia.model.TokenResponse
import com.example.olymperia.network.StravaAuthService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val CLIENT_ID     = "157130"
        private const val CLIENT_SECRET = "4999cea79272497103064b92f627d37e06d89cdc"
        private const val REDIRECT_URI  = "olymperia://vulturia.com"
    }

    private val authService: StravaAuthService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.strava.com/oauth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StravaAuthService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btnStravaLogin).setOnClickListener {



            val uri = Uri.Builder()
                .scheme("https")
                .authority("www.strava.com")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("redirect_uri", REDIRECT_URI)
                .appendQueryParameter("approval_prompt", "auto")
                .appendQueryParameter("scope", "read,activity:read_all")
                .build()
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        intent.data?.getQueryParameter("code")?.let { code ->
            exchangeCodeForToken(code)
        } ?: run {
            Toast.makeText(this, "Error en OAuth: no vino code", Toast.LENGTH_LONG).show()
        }

    }

    private fun exchangeCodeForToken(code: String) {
        lifecycleScope.launch {
            try {
                val resp: TokenResponse = authService.exchangeToken(
                    clientId     = CLIENT_ID,
                    clientSecret = CLIENT_SECRET,
                    code         = code,
                    redirectUri  = REDIRECT_URI
                )
                // Guarda tokens en prefs
                getSharedPreferences("strava_prefs", MODE_PRIVATE)
                    .edit()
                    .putString("access_token", resp.accessToken)
                    .putString("refresh_token", resp.refreshToken)
                    .putLong("expires_at", resp.expiresAt)
                    .apply()

                // Lanza MainActivity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))  // ✅ CORREGIDO
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity,
                    "Auth error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
