package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.ItemSegmentBinding
import com.example.olymperia.model.Segment

class SegmentAdapter(
    private val segments: List<Segment>,
    private val onClick: (Segment) -> Unit
) : RecyclerView.Adapter<SegmentAdapter.SegmentViewHolder>() {

    inner class SegmentViewHolder(
        private val binding: ItemSegmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(segment: Segment) {
            binding.tvSegmentName.text = segment.name
            binding.root.setOnClickListener { onClick(segment) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentViewHolder {
        val binding = ItemSegmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SegmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        holder.bind(segments[position])
    }

    override fun getItemCount(): Int = segments.size
}
