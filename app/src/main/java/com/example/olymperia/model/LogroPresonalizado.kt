package com.example.olymperia.model

data class LogroPersonalizado(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val tipo: TipoLogro,
    val visible: Boolean = true
)
