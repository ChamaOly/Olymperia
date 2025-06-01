import android.content.Context
import com.example.olymperia.utils.ScoreManager
import com.example.olymperia.utils.HonorManager
import com.example.olymperia.model.Honor
import android.util.Log
import android.widget.Toast

import com.example.olymperia.utils.normalizeProvincia
import com.example.olymperia.utils.verificarClavesConquistadorActivas

fun resetYReconstruirLogros(context: Context) {
    // 1. Borrar progreso
    ScoreManager.resetAll(context)
    context.getSharedPreferences("honores", Context.MODE_PRIVATE).edit().clear().apply()

    Toast.makeText(context, "Usuario reiniciado. Reconstruyendo logros...", Toast.LENGTH_SHORT).show()

    // 2. Reconstruir logros desde esfuerzos previos
    HonorManager.verificarDesbloqueoDeHonores(context) { nuevoHonor ->
        Log.d("REBUILD", "✅ Honor recuperado: ${nuevoHonor.nombre}")
    }

    // 3. Verificar claves conquistador_* activas
    verificarClavesConquistadorActivas(context)
}
