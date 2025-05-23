package com.example.olymperia.model

data class UserProgress(
    val completedSegments: MutableSet<Long> = mutableSetOf(),
    var totalPoints: Int = 0
)
