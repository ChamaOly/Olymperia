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

        if (totalEfforts == 0) return null

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

            val detalle = nuevos.joinToString(" + ") {
                "$points puntos — $it vez${if (it > 1) "es" else ""}"
            }

            Log.d("SCORE", "✅ Segmento $segmentId realizado $totalEfforts veces → $detalle")

            HonorManager.verificarDesbloqueoDeHonores(context) { nuevoHonor ->
                onHonorUnlocked?.invoke(nuevoHonor)
            }

            detalle
        } else {
            prefs.edit().putInt("completed_count_$segmentId", totalEfforts).apply()

            // ✅ Verificar logros aunque no se hayan obtenido puntos nuevos
            HonorManager.verificarDesbloqueoDeHonores(context) { nuevoHonor ->
                onHonorUnlocked?.invoke(nuevoHonor)
            }

            Log.d("SCORE", "🔁 Segmento $segmentId → esfuerzos actualizados a $totalEfforts, sin puntos nuevos")
            null
        }
    }
    fun recalcularPuntuacionTotal(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        var total = 0

        val segmentos = com.example.olymperia.repository.PortRepository.getAllSegments()
        for (segment in segmentos) {
            val threshold = prefs.getInt("port_${segment.id}", 0)
            total += threshold * segment.points
        }

        editor.putInt(TOTAL_POINTS_KEY, total)
        editor.apply()
        Log.d("SCORE", "🔄 Puntuación total recalculada: $total")
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