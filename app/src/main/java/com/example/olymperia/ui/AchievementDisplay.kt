package com.example.olymperia.ui

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.olymperia.R
import com.example.olymperia.utils.AchievementManager

object AchievementDisplay {

    fun showAchievementDialog(context: Context, achievement: AchievementManager.Achievement) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_achievement_unlocked, null)

        val title = view.findViewById<TextView>(R.id.tvAchievementTitle)
        val icon = view.findViewById<ImageView>(R.id.ivAchievementIcon)
        val container = view.findViewById<ViewGroup>(R.id.achievementDialogContainer)

        title.text = achievement.name
        icon.visibility = ViewGroup.GONE

        val backgroundRes = when (achievement.id) {
            "conquistador" -> R.drawable.bg_honor_conquistador
            "senor_demanda" -> R.drawable.bg_honor_maestro
            else -> R.drawable.bg_honor_elite
        }
        container.setBackgroundResource(backgroundRes)

        dialog.setContentView(view)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        dialog.show()
    }
}

