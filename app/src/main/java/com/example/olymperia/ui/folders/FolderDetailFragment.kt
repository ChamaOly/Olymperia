package com.example.olymperia.ui.folders

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.R
import com.example.olymperia.model.Segment
import com.example.olymperia.ui.SegmentAdapter
import com.google.android.material.appbar.MaterialToolbar

class FolderDetailFragment : Fragment(R.layout.fragment_folder_detail) {

    companion object {
        private const val ARG_ID   = "folder_id"
        private const val ARG_NAME = "folder_name"

        /** Usa este método para instanciar el Fragment y pasarle los argumentos. */
        fun newInstance(folderId: Long, folderName: String): FolderDetailFragment {
            return FolderDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, folderId)
                    putString(ARG_NAME, folderName)
                }
            }
        }
    }

    private var folderId: Long = 0L
    private lateinit var toolbar: MaterialToolbar
    private lateinit var rvSegments: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Lee los argumentos de entrada
        val args = requireArguments()
        folderId = args.getLong(ARG_ID)
        val title = args.getString(ARG_NAME, "Carpeta")
        setupToolbar(view, title)

        // 2) Prepara el RecyclerView
        rvSegments = view.findViewById(R.id.rvSegments)
        rvSegments.layoutManager = LinearLayoutManager(requireContext())

        // 3) Carga los segmentos de esta carpeta
        val segments = cargarSegmentsDeCarpeta(folderId)

        // 4) Asigna el adapter y el listener de click
        rvSegments.adapter = SegmentAdapter(segments) { segment ->
            // aquí muestras el count o abres detalle
            Toast.makeText(requireContext(),
                "Segmento ${segment.name} seleccionado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /** Configura la toolbar: título dinámico + botón atrás */
    private fun setupToolbar(root: View, title: String) {
        toolbar = root.findViewById(R.id.toolbarFolder)
        toolbar.title = title
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /** TODO: reemplaza esta función por tu repositorio o base de datos real */
    private fun cargarSegmentsDeCarpeta(folderId: Long): List<Segment> {
        // Nota: el modelo Segment espera sólo (id: Long, name: String)
        return listOf(
            Segment(101L, "Puerto de XYZ"),
            Segment(102L, "Alto de ABC")
        )
    }
}
