// app/src/main/java/com/example/olymperia/model/SegmentEffort.kt
package com.example.olymperia.model

import com.google.gson.annotations.SerializedName

data class SegmentEffort(
    @SerializedName("id")           val id: Long,
    @SerializedName("elapsed_time") val elapsedTime: Int,
    @SerializedName("start_date")   val startDate: String
)
