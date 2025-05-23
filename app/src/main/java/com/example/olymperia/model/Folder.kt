package com.example.olymperia.model

import androidx.annotation.DrawableRes

data class Folder(
    val id: Long,
    val name: String,
    @DrawableRes val iconRes: Int
)
