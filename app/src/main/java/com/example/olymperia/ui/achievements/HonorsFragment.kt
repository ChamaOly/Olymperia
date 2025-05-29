package com.example.olymperia.ui.achievements

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentHonorsBinding
import com.example.olymperia.model.Honor
import com.example.olymperia.ui.HonorAdapter
import com.example.olymperia.ui.HonorUnlockedDialog
import com.example.olymperia.utils.HonorManager

class HonorsFragment : Fragment() {

    private var _binding: FragmentHonorsBinding? = null
    private val binding get() = _binding!!

    private val listaDeHonores = mutableListOf<Honor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHonorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaDeHonores.clear()
        listaDeHonores.addAll(cargarHonoresDesbloqueados())

        binding.recyclerHonores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHonores.adapter = HonorAdapter(listaDeHonores) { honor ->
            HonorManager.guardarHonorDestacado(requireContext(), honor.nombre)
            Toast.makeText(requireContext(), "Honor destacado: ${honor.nombre}", Toast.LENGTH_SHORT).show()
        }

        HonorManager.verificarDesbloqueoDeHonores(requireContext()) { honorNuevo ->
            val existe = listaDeHonores.any { it.nombre == honorNuevo.nombre }
            if (!existe) {
                listaDeHonores.add(honorNuevo)
                binding.recyclerHonores.adapter?.notifyItemInserted(listaDeHonores.size - 1)
            }

            val dialog = HonorUnlockedDialog(honorNuevo) {}
            dialog.show(childFragmentManager, "HONOR_DIALOG")
        }
    }

    private fun cargarHonoresDesbloqueados(): List<Honor> {
        val honores = mutableListOf<Honor>()
        val prefs = requireContext().getSharedPreferences("honores", Context.MODE_PRIVATE)
        prefs.all.forEach { (clave, valor) ->
            if (valor == true && !clave.startsWith("honor_destacado")) {
                val tipo = when {
                    clave.startsWith("guia") -> "guia"
                    clave.startsWith("rey") -> "rey"
                    clave.startsWith("conquistador") -> "conquistador"
                    else -> "elite"
                }
                val nombreFormateado = clave.replace("_", " ").replaceFirstChar { it.uppercaseChar() }
                honores.add(Honor(nombreFormateado.replace("honor", "").trim(), tipo, true))
            }
        }
        return honores
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
