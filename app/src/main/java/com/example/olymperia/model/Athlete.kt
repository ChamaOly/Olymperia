package com.example.olymperia.model

data class Athlete(
    val id: Long,
    val username: String,
    val firstname: String,
    val lastname: String,
    val city: String?,
    val country: String?,
    val sex: String?,
    val premium: Boolean,
    val profile_medium: String,
    // añade aquí más campos que Strava devuelva,
    // según tu JSON de /athlete (mira la docs de Strava)
)