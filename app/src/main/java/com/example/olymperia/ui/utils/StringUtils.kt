package com.example.olymperia.utils

fun normalizeProvincia(nombre: String): String {
    return nombre.trim().lowercase()
        .replace("á", "a")
        .replace("é", "e")
        .replace("í", "i")
        .replace("ó", "o")
        .replace("ú", "u")
        .replace("ü", "u")
        .replace("ñ", "n")
        .replace(" ", "_")
        .replace("-", "_")
}
