package com.example.olymperia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView  // ⬅️ Añadido para resolver errores
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R
import com.example.olymperia.model.RankingUser

class RankingAdapter(private val usuarios: List<RankingUser>) :
    RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val nivel: TextView = view.findViewById(R.id.tvNivel)
        val puntos: TextView = view.findViewById(R.id.tvPuntos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.nombre.text = usuario.nombre
        holder.nivel.text = "Nivel ${usuario.nivel}"
        holder.puntos.text = "${usuario.puntos} puntos"
    }

    override fun getItemCount(): Int = usuarios.size
}
