// app/src/main/java/com/example/olymperia/ui/EffortAdapter.kt
package com.example.olymperia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.ItemEffortBinding
import com.example.olymperia.model.SegmentEffort

class EffortAdapter(private val items: List<SegmentEffort>)
    : RecyclerView.Adapter<EffortAdapter.VH>() {

    inner class VH(val b: ItemEffortBinding)
        : RecyclerView.ViewHolder(b.root) {
        fun bind(e: SegmentEffort) {
            b.tvElapsed.text   = "${e.elapsedTime}s"
            b.tvStartDate.text = e.startDate
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int) = VH(
        ItemEffortBinding.inflate(LayoutInflater.from(p.context), p, false)
    )
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(items[pos])
    override fun getItemCount() = items.size
}
