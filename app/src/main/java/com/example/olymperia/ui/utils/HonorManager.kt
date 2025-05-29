package com.example.olymperia.utils

import android.content.Context
import com.example.olymperia.model.Honor
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.utils.ScoreManager

object HonorManager {

    fun marcarHonorComoDesbloqueado(context: Context, id: String) {
        val prefs = context.getSharedPreferences("honores", Context.MODE_PRIVATE)
        prefs.edit().putBoolean(id, true).apply()
    }

    fun estaHonorDesbloqueado(context: Context, id: String): Boolean {
        return context.getSharedPreferences("honores", Context.MODE_PRIVATE)
            .getBoolean(id, false)
    }

    fun guardarHonorDestacado(context: Context, nombre: String) {
        context.getSharedPreferences("honores", Context.MODE_PRIVATE)
            .edit()
            .putString("honor_destacado", nombre)
            .apply()
    }

    fun obtenerHonorDestacado(context: Context): String? {
        return context.getSharedPreferences("honores", Context.MODE_PRIVATE)
            .getString("honor_destacado", null)
    }

    fun verificarDesbloqueoDeHonores(context: Context, onNuevoHonor: (Honor) -> Unit) {
        val segmentos = PortRepository.getAllSegments()

        // Guía de Buitres: 10 puertos únicos de categoría 1
        val unicosCat1 = segmentos.filter {
            it.points in 171..250 && ScoreManager.getCompletedCount(context, it.id) > 0
        }.map { it.id }.toSet()

        if (unicosCat1.size >= 10 && !estaHonorDesbloqueado(context, "guia_buitres")) {
            marcarHonorComoDesbloqueado(context, "guia_buitres")
            val honor = Honor("Guía de Buitres", "guia", true)
            onNuevoHonor(honor)
        }

        // Conquistador de Burgos: todos los segmentos de Burgos completados
        val deBurgos = segmentos.filter { it.province == "Burgos" }
        val completados = deBurgos.count { ScoreManager.getCompletedCount(context, it.id) > 0 }

        if (completados == deBurgos.size && !estaHonorDesbloqueado(context, "conquistador_burgos")) {
            marcarHonorComoDesbloqueado(context, "conquistador_burgos")
            val honor = Honor("Conquistador de Burgos", "conquistador", true)
            onNuevoHonor(honor)
        }


        val provincias = segmentos.map { it.province }.distinct()

        
        for (provincia in provincias) {
            val idHonor = "conquistador_${provincia.lowercase()}"
            val completadosProvincia = segmentos.filter {
                it.province == provincia && ScoreManager.getCompletedCount(context, it.id) > 0
            }

            if (completadosProvincia.isNotEmpty() && !estaHonorDesbloqueado(context, idHonor)) {
                android.util.Log.d("HONOR_DEBUG", "🔓 Desbloqueando: $idHonor")
                marcarHonorComoDesbloqueado(context, idHonor)
                context.getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
                    .edit().putBoolean(idHonor, true).apply()
                val honor = Honor("Conquistador de $provincia", "conquistador", true)
                onNuevoHonor(honor)
            }
        }


    }
}
