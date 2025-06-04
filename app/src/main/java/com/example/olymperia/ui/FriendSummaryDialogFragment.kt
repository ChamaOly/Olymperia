package com.example.olymperia.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.olymperia.R
import com.example.olymperia.databinding.DialogFriendSummaryBinding
import com.example.olymperia.model.RankingUser

class FriendSummaryDialogFragment(private val amigo: RankingUser) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogFriendSummaryBinding.inflate(LayoutInflater.from(context))

        binding.tvNombre.text = amigo.nombre
        binding.tvNivel.text = "Nivel ${amigo.nivel}"
        binding.tvPuntos.text = "${amigo.puntos} puntos"
        binding.tvDivision.text = "División ${getDivision(amigo.nivel)}"

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Cerrar") { dialogInterface, _ -> dialogInterface.dismiss() }
            .create()
    }

    private fun getDivision(nivel: Int): Int {
        return when {
            nivel in 0..10 -> 10
            nivel in 11..20 -> 9
            nivel in 21..30 -> 8
            nivel in 31..40 -> 7
            nivel in 41..50 -> 6
            nivel in 51..60 -> 5
            nivel in 61..70 -> 4
            nivel in 71..80 -> 3
            nivel in 81..90 -> 2
            nivel in 91..100 -> 1
            else -> 10
        }
    }
}
