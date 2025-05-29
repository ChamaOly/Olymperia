package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.ItemProvinciaBinding

class ProvinceAdapter(
    private val provincias: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProvinciaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProvinciaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provincia = provincias[position]
        holder.binding.tvNombreProvincia.text = provincia
        holder.binding.root.setOnClickListener { onClick(provincia) }
    }

    override fun getItemCount() = provincias.size
}
