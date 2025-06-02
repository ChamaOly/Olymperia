package com.example.olymperia.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.olymperia.UserProgress
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

object UserProgressManager {

    private const val PREF_NAME = "user_progress"
    private const val KEY_PROGRESS = "progress_data"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveProgress(context: Context, progress: UserProgress) {
        val json = Gson().toJson(progress)
        getPrefs(context).edit().putString(KEY_PROGRESS, json).apply()
    }

    fun loadProgress(context: Context): UserProgress {
        val json = getPrefs(context).getString(KEY_PROGRESS, null)
        return if (json != null) {
            Gson().fromJson(json, UserProgress::class.java)
        } else {
            UserProgress()
        }
    }

    fun addCompletedSegment(context: Context, segmentId: Long) {
        val progress = loadProgress(context)
        val currentCount = progress.segmentCompletionCounts.getOrDefault(segmentId, 0)
        progress.segmentCompletionCounts[segmentId] = currentCount + 1
        progress.segmentCompletionDates[segmentId] = Date().time
        saveProgress(context, progress)
    }

    fun getCompletionCount(context: Context, segmentId: Long): Int {
        val progress = loadProgress(context)
        return progress.segmentCompletionCounts.getOrDefault(segmentId, 0)
    }

    fun hasCompletedPorts(context: Context, requiredIds: List<Long>): Boolean {
        val progress = loadProgress(context)
        return requiredIds.all { progress.segmentCompletionCounts.getOrDefault(it, 0) > 0 }
    }

    fun getTotalPoints(context: Context): Int {
        return loadProgress(context).totalPoints
    }

    fun addPoints(context: Context, points: Int) {
        val progress = loadProgress(context)
        progress.totalPoints += points
        saveProgress(context, progress)
    }

    fun getCompletionDate(context: Context, segmentId: Long): Long? {
        val progress = loadProgress(context)
        return progress.segmentCompletionDates[segmentId]
    }
}
