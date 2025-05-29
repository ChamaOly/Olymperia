package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.ItemProvinciaBinding

class RegionAdapter(
    private val regiones: List<Pair<String, List<String>>>,
    private val onClick: (List<String>) -> Unit
) : RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    class RegionViewHolder(val binding: ItemProvinciaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemProvinciaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val (nombreRegion, provincias) = regiones[position]
        holder.binding.tvNombreProvincia.text = nombreRegion
        holder.binding.root.setOnClickListener { onClick(provincias) }
    }

    override fun getItemCount(): Int = regiones.size
}
