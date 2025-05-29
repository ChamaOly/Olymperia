package com.example.olymperia.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")  val accessToken:  String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_at")    val expiresAt:    Long,
    @SerializedName("expires_in")    val expiresIn:    Int,
    @SerializedName("athlete")       val athlete:      Athlete
)

