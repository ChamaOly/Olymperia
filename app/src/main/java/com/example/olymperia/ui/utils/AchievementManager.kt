package com.example.olymperia.utils

import android.content.Context
import com.example.olymperia.model.LogroPersonalizado
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.olymperia.UserProgress

object AchievementManager {

    private var logros: List<LogroPersonalizado> = emptyList()

    data class Achievement(
        val id: String,
        val name: String,
        val requiredPorts: List<Long>,
        var unlocked: Boolean = false
    )

    private val achievements = mutableListOf(
        Achievement(
            id = "cetro_demanda",
            name = "Cetro de la Demanda",
            requiredPorts = listOf(34343L, 3534L, 334L, 4545L)
        ),

        // Puedes añadir más logros aquí
    )

    fun cargarLogrosDesdeJson(context: Context) {
        val assetManager = context.assets
        val inputStream = assetManager.open("logros.json")
        val json = inputStream.bufferedReader().use { it.readText() }

        val type = object : TypeToken<List<LogroPersonalizado>>() {}.type
        logros = Gson().fromJson(json, type)
    }

    fun obtenerTodosLosLogros(): List<LogroPersonalizado> {
        return logros
    }

    fun checkAndUnlockAchievements(context: Context): List<Achievement> {
        val unlockedNow = mutableListOf<Achievement>()
        val progress = UserProgressManager.loadProgress(context)

        for (achievement in achievements) {
            if (!achievement.unlocked && achievement.requiredPorts.all { progress.segmentCompletionCounts[it] ?: 0 > 0 }) {
                achievement.unlocked = true
                unlockedNow.add(achievement)
            }
        }

        return unlockedNow
    }

    fun getAllAchievements(): List<Achievement> = achievements
}
