package com.example.olymperia.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R

class HighlightedTrophyAdapter(private val trophies: List<String>) :
    RecyclerView.Adapter<HighlightedTrophyAdapter.TrophyViewHolder>() {

    inner class TrophyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trophyText: TextView = view.findViewById(R.id.tvTrophyName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrophyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_highlighted_trophy, parent, false)
        return TrophyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrophyViewHolder, position: Int) {
        holder.trophyText.text = trophies[position]
    }

    override fun getItemCount(): Int = trophies.size
}
