package com.example.olymperia.model

data class Activity(
    val id: Long,
    val name: String,
    val elapsed_time: Int,
    // añade aquí los campos que necesites del JSON de Strava...
)
