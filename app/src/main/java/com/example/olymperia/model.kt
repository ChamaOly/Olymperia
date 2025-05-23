package com.example.olymperia.model

/**
 * Representa un esfuerzo en un segmento de Strava
 *
 * @param segmentId   ID numérico del segmento
 * @param segmentName Nombre descriptivo del segmento
 * @param elapsedTime Tiempo que tardó el atleta en completarlo (en segundos)
 */
data class Effort(
    val segmentId: Long,
    val segmentName: String,
    val elapsedTime: Int
)
