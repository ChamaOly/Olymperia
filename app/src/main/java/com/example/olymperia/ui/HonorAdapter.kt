package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R
import com.example.olymperia.model.Honor


class HonorAdapter(
    private val honors: List<Honor>,
    private val onClick: (Honor) -> Unit
) : RecyclerView.Adapter<HonorAdapter.HonorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HonorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_honor, parent, false)
        return HonorViewHolder(view)
    }

    override fun onBindViewHolder(holder: HonorViewHolder, position: Int) {
        holder.bind(honors[position])
    }

    override fun getItemCount(): Int = honors.size

    inner class HonorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHonor: TextView = itemView.findViewById(R.id.tvHonorNombre)

        fun bind(honor: Honor) {
            val context = itemView.context
            val id = when {
                honor.nombre.startsWith("Conquistador de ") -> "conquistador_" + honor.nombre.removePrefix("Conquistador de ").lowercase()

                honor.nombre.startsWith("Rey de ") -> "rey_" + honor.nombre.removePrefix("Rey de ").lowercase()
                else -> honor.nombre.lowercase().replace(" ", "_")
            }

            val estaDesbloqueado = com.example.olymperia.utils.HonorManager.estaHonorDesbloqueado(context, id)
            honor.desbloqueado = estaDesbloqueado
            tvHonor.text = honor.nombre
            tvHonor.alpha = if (estaDesbloqueado) 1f else 0.3f

            val fondo = when (honor.tipo) {
                "conquistador" -> R.drawable.bg_honor_conquistador
                "rey" -> R.drawable.bg_honor_rey
                "guia" -> R.drawable.bg_honor_guia
                "maestro" -> R.drawable.bg_honor_maestro
                "elite" -> R.drawable.bg_honor_elite
                else -> R.drawable.bg_honor_conquistador
            }
            tvHonor.setBackgroundResource(fondo)

            itemView.setOnClickListener {
                if (estaDesbloqueado) onClick(honor)
            }
        }
    }
}