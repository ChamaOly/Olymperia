package com.example.olymperia.ui.folders


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R
import com.example.olymperia.model.Folder

/**
 * Adapter para mostrar una lista de carpetas en un RecyclerView.
 *
 * @param items Lista de objetos Folder a mostrar.
 * @param onClick Lambda que se invoca cuando el usuario clickea una carpeta.
 */
class FolderAdapter(
    private val items: List<Folder>,
    private val onClick: (Folder) -> Unit
) : RecyclerView.Adapter<FolderAdapter.ViewHolder>() {

    /** ViewHolder que mantiene las referencias de vistas de cada item_folder.xml */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view as CardView
        val ivIcon: ImageView = view.findViewById(R.id.ivFolderIcon)
        val tvName: TextView = view.findViewById(R.id.tvFolderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el layout item_folder.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_folder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val folder = items[position]

        // Asigna icono y nombre
        holder.ivIcon.setImageResource(folder.iconRes)
        holder.tvName.text = folder.name

        // Click listener
        holder.card.setOnClickListener {
            onClick(folder)
        }
    }

    override fun getItemCount(): Int = items.size
}
