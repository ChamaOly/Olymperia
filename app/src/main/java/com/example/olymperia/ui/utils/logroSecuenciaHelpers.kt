package com.example.olymperia.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.example.olymperia.R
import com.example.olymperia.ui.AchievementDisplay

object LogroSecuenciaHelper {

    fun mostrarLogrosEnSecuencia(context: Context, logros: List<AchievementManager.Achievement>, index: Int = 0) {
        if (index >= logros.size) return

        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_achievement_unlocked, null)

        val title = view.findViewById<TextView>(R.id.tvAchievementTitle)
        val icon = view.findViewById<ImageView>(R.id.ivAchievementIcon)

        val logro = logros[index]
        title.text = logro.name


        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.setOnDismissListener {
            mostrarLogrosEnSecuencia(context, logros, index + 1)
        }

        dialog.show()
    }
}
