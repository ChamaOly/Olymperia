package com.example.olymperia

data class UserProgress(
    val completedSegments: MutableSet<Long> = mutableSetOf(),
    val segmentCompletionCounts: MutableMap<Long, Int> = mutableMapOf(),
    val segmentCompletionDates: MutableMap<Long, Long> = mutableMapOf(),

    var totalPoints: Int = 0
)
