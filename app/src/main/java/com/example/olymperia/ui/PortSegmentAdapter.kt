package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.ItemSegmentBinding
import com.example.olymperia.model.PortSegment
import com.example.olymperia.R

class PortSegmentAdapter(
    private val onClick: (PortSegment) -> Unit
) : ListAdapter<PortSegment, PortSegmentAdapter.PortSegmentViewHolder>(DiffCallback) {

    inner class PortSegmentViewHolder(private val binding: ItemSegmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(port: PortSegment) {
            val context = binding.root.context

            // Nombre
            binding.tvSegmentName.text = port.name

            // Repeticiones
            val repeticiones = port.completedCount
            binding.tvSegmentCount.text = "x$repeticiones"

            // Categoría
            val categoria = when {
                port.points < 100 -> "3"
                port.points in 100..170 -> "2"
                port.points in 171..250 -> "1"
                port.points in 251..1000 -> "HC"
                else -> "0"
            }

            // Fondo según categoría
            val fondoRes = when (categoria) {
                "3" -> R.drawable.bg_categoria_3
                "2" -> R.drawable.bg_categoria_2
                "1" -> R.drawable.bg_categoria_1
                "HC" -> R.drawable.bg_categoria_hc
                else -> R.drawable.bg_categoria_1
            }
            binding.layoutSegmentContainer.setBackgroundResource(fondoRes)

            // Insignia
            val badgeLevel = when {
                repeticiones >= 10 -> 4
                repeticiones >= 5 -> 3
                repeticiones >= 3 -> 2
                repeticiones >= 1 -> 1
                else -> 0
            }

            if (badgeLevel > 0) {
                val badgeName = "badge_${categoria.lowercase()}_$badgeLevel"
                val badgeResId = context.resources.getIdentifier(badgeName, "drawable", context.packageName)
                if (badgeResId != 0) {
                    binding.ivSegmentBadge.setImageResource(badgeResId)
                    binding.ivSegmentBadge.visibility = View.VISIBLE
                    binding.ivSegmentBadge.alpha = 0f
                    binding.ivSegmentBadge.animate().alpha(1f).setDuration(400).start()
                } else {
                    binding.ivSegmentBadge.visibility = View.GONE
                }
            } else {
                binding.ivSegmentBadge.visibility = View.GONE
            }

            // Animar contador
            binding.tvSegmentCount.alpha = 0f
            binding.tvSegmentCount.animate().alpha(1f).setDuration(400).start()

            // Animar tarjeta (pulse)
            binding.root.scaleX = 1f
            binding.root.scaleY = 1f
            binding.root.animate()
                .scaleX(1.05f)
                .scaleY(1.05f)
                .setDuration(150)
                .withEndAction {
                    binding.root.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()
                }.start()

            // Click
            binding.root.setOnClickListener {
                onClick(port)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortSegmentViewHolder {
        val binding = ItemSegmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PortSegmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PortSegmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PortSegment>() {
            override fun areItemsTheSame(oldItem: PortSegment, newItem: PortSegment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PortSegment, newItem: PortSegment): Boolean {
                return oldItem == newItem
            }
        }
    }
}
