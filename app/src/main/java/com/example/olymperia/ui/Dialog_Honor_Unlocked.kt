package com.example.olymperia.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.olymperia.R
import com.example.olymperia.model.Honor

class HonorUnlockedDialog(
    private val honor: Honor,
    private val onAceptar: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_honor_unlocked, null)

        val tvMensaje = view.findViewById<TextView>(R.id.tvTextoHonor)
        val mediaPlayer = android.media.MediaPlayer.create(requireContext(), R.raw.honor_unlocked)
        mediaPlayer.start()

        // Animación de escala
        view.scaleX = 0.5f
        view.scaleY = 0.5f
        view.animate().scaleX(1f).scaleY(1f).setDuration(300).start()

        val fondo = when (honor.tipo) {
            "conquistador" -> R.drawable.bg_honor_conquistador
            "rey" -> R.drawable.bg_honor_rey
            "guia" -> R.drawable.bg_honor_guia
            "maestro" -> R.drawable.bg_honor_maestro
            "elite" -> R.drawable.bg_honor_elite
            else -> R.drawable.bg_honor_conquistador
        }

        tvMensaje.text = honor.nombre
        tvMensaje.setBackgroundResource(fondo)

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ -> onAceptar() }
            .create()
    }
}
