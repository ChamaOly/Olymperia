package com.example.olymperia.utils

import android.content.Context
import com.example.olymperia.UserProgress

object AchievementManager {

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
