package com.example.olymperia.model

data class Honor(
    val nombre: String,
    val tipo: String, // "conquistador", "rey", "guia", "maestro", "elite"
    val desbloqueado: Boolean
)
