package com.example.olymperia.utils

import android.content.Context
import android.util.Log

object ScoreManager {

    private const val PREFS_NAME = "strava_prefs"
    private const val TOTAL_POINTS_KEY = "total_points"
    private val validThresholds = listOf(1, 3, 5, 10)

    fun addPointsIfEligible(
        context: Context,
        segmentId: Long,
        completedCount: Int,
        points: Int
    ): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastThreshold = prefs.getInt("port_$segmentId", 0)

        val newThresholds = validThresholds.filter { it > lastThreshold && completedCount >= it }

        return if (newThresholds.isNotEmpty()) {
            val newHighest = newThresholds.last()
            val total = prefs.getInt(TOTAL_POINTS_KEY, 0) + (newThresholds.size * points)
            prefs.edit()
                .putInt(TOTAL_POINTS_KEY, total)
                .putInt("port_$segmentId", newHighest)
                .apply()

            val detail =
                newThresholds.joinToString(" + ") { "$points puntos — $it vez${if (it > 1) "es" else ""}" }

            Log.d(
                "SCORE",
                "✅ Segmento $segmentId completado $completedCount veces → $detail (nuevo umbral $newHighest)"
            )
            detail
        } else {
            Log.d(
                "SCORE",
                "🔁 Segmento $segmentId completado $completedCount veces → sin puntos (ya registrado hasta $lastThreshold)"
            )
            null
        }
        Log.d(
            "SCORE",
            "🔁 Segmento $segmentId completado $completedCount veces → sin puntos (ya registrado hasta $lastThreshold)"
        )
        false
    }

    fun incrementarRepeticion(context: Context, segmentId: Long) {
        val prefs = context.getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
        val clave = "completed_count_$segmentId"
        val actual = prefs.getInt(clave, 0)
        prefs.edit().putInt(clave, actual + 1).apply()
    }

    fun getTotalPoints(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(TOTAL_POINTS_KEY, 0)
    }

    fun getUserLevel(context: Context): Int {
        return getTotalPoints(context) / 200
    }

    fun getCompletedCount(context: Context, segmentId: Long): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt("completed_count_$segmentId", 0)
    }

    fun incrementCompletedCount(context: Context, segmentId: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val count = prefs.getInt("completed_count_$segmentId", 0) + 1
        prefs.edit().putInt("completed_count_$segmentId", count).apply()
    }

    fun resetAll(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

}





