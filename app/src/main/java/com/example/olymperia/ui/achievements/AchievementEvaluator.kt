package com.example.olymperia.ui.achievements

import android.content.Context
import android.util.Log
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.utils.ScoreManager

object AchievementEvaluator {

    fun evaluarLogrosPorProvincia(
        context: Context,
        effortsPorSegmento: Map<Long, List<Any>> // el tipo real es SegmentEffort
    ) {
        val prefs = context.getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val segmentos = PortRepository.getAllSegments()
        val porProvincia = segmentos
            .filter { it.points >= 100 }
            .groupBy { it.province }

        porProvincia.forEach { (provinciaOriginal, puertos) ->
            val provincia = provinciaOriginal.lowercase().replace(" ", "_")
            Log.d("LOGROS", "Evaluando $provincia: ${puertos.size} puertos posibles")

            val completadosEnProvincia = puertos.count { port ->
                val efforts = effortsPorSegmento[port.id]
                val tieneEffort = efforts != null && efforts.isNotEmpty()
                if (tieneEffort) {
                    Log.d("LOGROS", "✔ ${port.name} completado (${port.id})")
                    ScoreManager.addPointsIfEligible(context, port.id, 1, port.points)
                }

                tieneEffort
            }


            Log.d("LOGROS", "→ Completados en $provincia: $completadosEnProvincia/${puertos.size}")

            if (completadosEnProvincia >= 1 && !prefs.getBoolean("conquistador_$provincia", false)) {
                Log.d("LOGROS", "✅ CONQUISTADOR logrado en $provincia")
                editor.putBoolean("conquistador_$provincia", true)
            }

            if (completadosEnProvincia == puertos.size && !prefs.getBoolean("rey_$provincia", false)) {
                Log.d("LOGROS", "👑 REY logrado en $provincia")
                editor.putBoolean("rey_$provincia", true)
            }
        }

        editor.apply()
    }
}
