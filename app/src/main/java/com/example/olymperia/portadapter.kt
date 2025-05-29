package com.example.olymperia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.model.PortSegment
import com.example.olymperia.utils.ScoreManager

class PortAdapter(
    private val ports: List<PortSegment>,
    private val onClick: (PortSegment) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<PortAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puerto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = ports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val port = ports[position]
        holder.bind(port)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre = itemView.findViewById<TextView>(R.id.tvNombrePuerto)
        private val ivCompletado = itemView.findViewById<ImageView>(R.id.ivCompletado)
        private val tvRepeticiones = itemView.findViewById<TextView>(R.id.tvRepeticiones)

        fun bind(port: PortSegment) {
            val nombre = port.name
            val puntos = port.points
            val segmentId = port.id

            val vecesCompletado = ScoreManager.getCompletedCount(context, segmentId)

            tvNombre.text = nombre
            tvRepeticiones.text = "x$vecesCompletado"

            val categoria = when {
                puntos < 85 -> "3"
                puntos in 100..170 -> "2"
                puntos in 171..250 -> "1"
                puntos in 251..1000 -> "HC"
                else -> "0"
            }

            val fondo = when (categoria) {
                "3" -> R.drawable.bg_categoria_3
                "2" -> R.drawable.bg_categoria_2
                "1" -> R.drawable.bg_categoria_1
                "HC" -> R.drawable.bg_categoria_hc
                else -> R.drawable.bg_categoria_1
            }
            itemView.setBackgroundResource(fondo)

            val iconRes = when {
                vecesCompletado >= 10 -> R.drawable.ic_crown
                vecesCompletado >= 5 -> R.drawable.ic_diamond
                vecesCompletado >= 3 -> R.drawable.ic_star
                vecesCompletado >= 1 -> R.drawable.ic_check
                else -> 0
            }

            if (iconRes != 0) {
                ivCompletado.setImageResource(iconRes)
                ivCompletado.visibility = View.VISIBLE
            } else {
                ivCompletado.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onClick(port)
            }
        }
    }
}
