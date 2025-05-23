// app/src/main/java/com/example/olymperia/model/TokenResponse.kt
package com.example.olymperia.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")  val accessToken:  String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_at")    val expiresAt:    Long,
    @SerializedName("expires_in")    val expiresIn:    Int
)
