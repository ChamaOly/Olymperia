package com.example.olymperia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.model.PortSegment

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
        private val ivInsignia = itemView.findViewById<ImageView>(R.id.ivInsignia)
        private val tvRepeticiones = itemView.findViewById<TextView>(R.id.tvRepeticiones)

        fun bind(port: PortSegment) {
            val nombre = port.name
            val puntos = port.points
            val segmentId = port.id.toString() // usamos 'id' como segmentId
            val vecesCompletado = port.completedCount

            tvNombre.text = nombre
            tvRepeticiones.text = "x$vecesCompletado"

            // Asignar categoría
            val categoria = when {
                puntos < 85 -> "3"
                puntos in 100..170 -> "2"
                puntos in 171..250 -> "1"
                puntos in 251..1000 -> "HC"
                else -> "0"
            }

            // Asignar fondo dinámico
            val fondo = when (categoria) {
                "3" -> R.drawable.bg_categoria_3
                "2" -> R.drawable.bg_categoria_2
                "1" -> R.drawable.bg_categoria_1
                "HC" -> R.drawable.bg_categoria_hc
                else -> R.drawable.bg_categoria_1 // nunca debería llegar aquí
            }
            itemView.setBackgroundResource(fondo)

            // Determinar insignia
            val badgeLevel = when {
                vecesCompletado >= 10 -> 4
                vecesCompletado >= 5 -> 3
                vecesCompletado >= 3 -> 2
                vecesCompletado >= 1 -> 1
                else -> 0
            }

            if (badgeLevel > 0) {
                val badgeName = "badge_${categoria.lowercase()}_$badgeLevel"
                val badgeResId = context.resources.getIdentifier(badgeName, "drawable", context.packageName)
                if (badgeResId != 0) {
                    ivInsignia.setImageResource(badgeResId)
                    ivInsignia.visibility = View.VISIBLE
                } else {
                    ivInsignia.visibility = View.GONE
                }
            } else {
                ivInsignia.visibility = View.GONE
            }

            // Click
            itemView.setOnClickListener {
                onClick(port)
            }
        }
    }
}
