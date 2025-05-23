package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R

class ProvinceAdapter(

    private val provinces: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(R.id.tvNombreProvincia)


        fun bind(province: String) {
            nameText.text = province
            itemView.setOnClickListener { onClick(province) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provincia, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = provinces.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(provinces[position])
    }
}
