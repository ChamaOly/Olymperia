package com.example.olymperia.utils

import android.content.Context
import android.util.Log

fun verificarClavesConquistadorActivas(context: Context) {
    val prefs = context.getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)
    val provincias = listOf(
        "alava", "albacete", "alicante", "almeria", "avila", "badajoz", "barcelona", "burgos",
        "caceres", "cadiz", "castellon", "ciudad_real", "cordoba", "a_coruna", "cuenca", "girona",
        "granada", "guadalajara", "guipuzkoa", "huelva", "huesca", "jaen", "leon", "lleida", "lugo",
        "madrid", "malaga", "murcia", "navarra", "ourense", "palencia", "las_palmas", "pontevedra",
        "la_rioja", "salamanca", "segovia", "sevilla", "soria", "tarragona", "santa_cruz_tenerife",
        "teruel", "toledo", "valencia", "valladolid", "bizkaia", "zamora", "zaragoza",
        "ceuta", "melilla", "cantabria", "asturias"
    )

    provincias.forEach { clave ->
        val id = "conquistador_" + normalizeProvincia(clave)
        val activo = prefs.getBoolean(id, false)
        Log.d("CLAVES_TEST", "$id → ${if (activo) "✅ activo" else "❌ inactivo"}")
    }
}
