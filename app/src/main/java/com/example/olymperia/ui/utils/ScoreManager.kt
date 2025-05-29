package com.example.olymperia.utils

import android.content.Context
import android.util.Log
import com.example.olymperia.model.Honor

object ScoreManager {

    private const val PREFS_NAME = "strava_prefs"
    private const val TOTAL_POINTS_KEY = "total_points"
    private val validThresholds = listOf(1, 3, 5, 10)

    fun procesarEsfuerzosStrava(
        context: Context,
        segmentId: Long,
        totalEfforts: Int,
        points: Int,
        onHonorUnlocked: ((Honor) -> Unit)? = null
    ): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val completedCountLocal = prefs.getInt("completed_count_$segmentId", 0)

        // ⚠️ Evita clics infinitos: solo continúa si hay nuevos esfuerzos reales en Strava
        if (totalEfforts <= completedCountLocal) {
            Log.d("SCORE", "⚠️ Segmento $segmentId sin esfuerzos nuevos. totalEfforts=$totalEfforts, local=$completedCountLocal")
            return null
        }

        val lastThreshold = prefs.getInt("port_$segmentId", 0)
        val nuevos = validThresholds.filter { it > lastThreshold && totalEfforts >= it }

        return if (nuevos.isNotEmpty()) {
            val nuevosPuntos = nuevos.size * points
            val total = prefs.getInt(TOTAL_POINTS_KEY, 0) + nuevosPuntos

            prefs.edit()
                .putInt(TOTAL_POINTS_KEY, total)
                .putInt("port_$segmentId", nuevos.last())
                .putInt("completed_count_$segmentId", totalEfforts)
                .apply()

            HonorManager.verificarDesbloqueoDeHonores(context) { nuevoHonor ->
                onHonorUnlocked?.invoke(nuevoHonor)
            }

            val detalle = nuevos.joinToString(" + ") {
                "$points puntos — $it vez${if (it > 1) "es" else ""}"
            }

            Log.d("SCORE", "✅ Segmento $segmentId realizado $totalEfforts veces → $detalle")
            detalle
        } else {
            prefs.edit().putInt("completed_count_$segmentId", totalEfforts).apply()
            Log.d("SCORE", "🔁 Segmento $segmentId → esfuerzos actualizados a $totalEfforts, sin puntos nuevos")
            null
        }
    }

    fun addPointsIfEligible(
        context: Context,
        segmentId: Long,
        completedCount: Int,
        points: Int,
        onHonorUnlocked: ((Honor) -> Unit)? = null
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

            HonorManager.verificarDesbloqueoDeHonores(context) { nuevoHonor ->
                onHonorUnlocked?.invoke(nuevoHonor)
            }

            val detail = newThresholds.joinToString(" + ") {
                "$points puntos — $it vez${if (it > 1) "es" else ""}"
            }

            Log.d("SCORE", "✅ Segmento $segmentId completado $completedCount veces → $detail (nuevo umbral $newHighest)")
            detail
        } else {
            Log.d("SCORE", "🔁 Segmento $segmentId completado $completedCount veces → sin puntos (ya registrado hasta $lastThreshold)")
            null
        }
    }

    fun incrementCompletedCount(context: Context, segmentId: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val count = prefs.getInt("completed_count_$segmentId", 0) + 1
        prefs.edit().putInt("completed_count_$segmentId", count).apply()
    }

    fun getHighestThresholdReached(context: Context, segmentId: Long): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt("port_$segmentId", 0)
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

    fun resetAll(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    private fun setCompletedCount(context: Context, segmentId: Long, count: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt("completed_count_$segmentId", count).apply()
    }
}
