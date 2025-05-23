package com.example.olymperia.model

data class PortSegment(
    val id: Long,               // Strava segment ID
    val name: String,
    val province: String,
    val points: Int,
    val completedCount: Int = 0 // Nuevo campo, con valor por defecto
)
