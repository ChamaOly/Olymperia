package com.example.olymperia.utils

import android.content.Context
import android.util.Log
import com.example.olymperia.model.Segment
import com.example.olymperia.repository.PortRepository

object AchievementEvaluator {
    fun evaluarLogrosPorProvincia(
        context: Context,
        effortsPorSegmento: Map<Long, List<Any>>
    ) {
        val prefs = context.getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val provincias = PortRepository.getAllSegments()
            .groupBy { it.province }

        provincias.forEach { (provincia, puertos) ->
            val completadosEnProvincia = puertos.count {
                effortsPorSegmento[it.id]?.isNotEmpty() == true
            }


            Log.d("LOGROS", "Completados en $provincia: $completadosEnProvincia de ${puertos.size}")

            if (completadosEnProvincia > 0 && !prefs.getBoolean("conquistador_$provincia", false)) {
                Log.d("LOGROS", "🏅 CONQUISTADOR logrado en $provincia")
                editor.putBoolean("conquistador_$provincia", true)
            }
        }

        editor.apply()
    }
}