package com.example.olymperia.ui.achievements

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.util.Log
import com.example.olymperia.databinding.FragmentMapAchievementsBinding
import com.example.olymperia.utils.normalizeProvincia

class MapAchievementsFragment : Fragment() {

    private var _binding: FragmentMapAchievementsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWebView()
    }

    private fun setupWebView() {
        val prefs = requireContext().getSharedPreferences("strava_prefs", Context.MODE_PRIVATE)

        val provincias = mapOf(
            "alava" to "ES-VI", "albacete" to "ES-AB", "alicante" to "ES-A", "almeria" to "ES-AL",
            "avila" to "ES-AV", "badajoz" to "ES-BA", "barcelona" to "ES-B", "burgos" to "ES-BU",
            "caceres" to "ES-CC", "cadiz" to "ES-CA", "castellon" to "ES-CS", "ciudad_real" to "ES-CR",
            "cordoba" to "ES-CO", "a_coruna" to "ES-C", "cuenca" to "ES-CU", "girona" to "ES-GI",
            "granada" to "ES-GR", "guadalajara" to "ES-GU", "guipuzkoa" to "ES-SS", "huelva" to "ES-H",
            "huesca" to "ES-HU", "jaen" to "ES-J", "leon" to "ES-LE", "lleida" to "ES-L", "lugo" to "ES-LU",
            "madrid" to "ES-M", "malaga" to "ES-MA", "murcia" to "ES-MU", "navarra" to "ES-NA",
            "ourense" to "ES-OR", "palencia" to "ES-P", "las_palmas" to "ES-GC", "pontevedra" to "ES-PO",
            "la_rioja" to "ES-LO", "salamanca" to "ES-SA", "segovia" to "ES-SG", "sevilla" to "ES-SE",
            "soria" to "ES-SO", "tarragona" to "ES-T", "santa_cruz_tenerife" to "ES-TF", "teruel" to "ES-TE",
            "toledo" to "ES-TO", "valencia" to "ES-V", "valladolid" to "ES-VA", "bizkaia" to "ES-BI",
            "zamora" to "ES-ZA", "zaragoza" to "ES-Z", "ceuta" to "ES-CE", "melilla" to "ES-ML",
            "cantabria" to "ES-CB", "asturias" to "ES-O"
        )

        val baseEstados = provincias.mapNotNull { (clave, svgId) ->
            val normalizedKey = normalizeProvincia(clave)
            Log.d("MAPA_DEBUG", "Verificando conquistador_$normalizedKey = ${prefs.getBoolean("conquistador_$normalizedKey", false)}")

            when {
                prefs.getBoolean("rey_$normalizedKey", false) -> "colorProvincia('$svgId', '#FFD700');"
                prefs.getBoolean("conquistador_$normalizedKey", false) -> "colorProvincia('$svgId', '#C0C0C0');"
                else -> null
            }
        }.toMutableList()

// Forzar Cantabria manualmente si corresponde
        if (prefs.getBoolean("conquistador_cantabria", false)) {
            Log.d("MAPA_DEBUG", "✅ Forzando pintado de Cantabria por separado")
            baseEstados.add("colorProvincia('ES-CB', '#C0C0C0');")
        }

        val estadosJS = baseEstados.joinToString("\n")

        // Parche específico: si conquistador_cantabria está activado, forzamos la coloración
        if (prefs.getBoolean("conquistador_cantabria", false)) {
            Log.d("MAPA_DEBUG", "✅ Forzando pintado de Cantabria por separado")
            estadosJS.plus("\\ncolorProvincia('ES-CB', '#C0C0C0');")
        }


        val html = requireContext().assets.open("mapa.html").bufferedReader().use { it.readText() }
        val finalHtml = html.replace("// ESTADOS_DINAMICOS", estadosJS)

        Log.d("MAPA_HTML", finalHtml.take(300))

        binding.webViewMapa.settings.javaScriptEnabled = true
        binding.webViewMapa.clearCache(true)
        binding.webViewMapa.loadDataWithBaseURL(null, finalHtml, "text/html", "utf-8", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
