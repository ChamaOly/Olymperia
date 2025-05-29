package com.example.olymperia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.olymperia.databinding.FragmentProvinceSelectorBinding

class ProvinceSelectorFragment : Fragment() {

    private var _binding: FragmentProvinceSelectorBinding? = null
    private val binding get() = _binding!!

    private val regiones = listOf(
        "Galicia" to listOf("A Coruña", "Lugo", "Pontevedra", "Ourense"),
        "Asturias" to listOf("Asturias"),
        "Cantabria" to listOf("Cantabria"),
        "La Rioja" to listOf("La Rioja"),
        "Euskadi" to listOf("Alava", "Bizkaia", "Guipuzkoa"),
        "Navarra" to listOf("Navarra"),
        "Pirineos" to listOf("Pirineos"),
        "Aragón" to listOf("Huesca", "Teruel", "Zaragoza"),
        "Cataluña" to listOf("Barcelona", "Girona", "Lleida", "Tarragona"),
        "Castilla y León" to listOf("Avila", "Burgos", "Leon", "Palencia", "Segovia", "Salamanca", "Soria", "Zamora"),
        "Madrid" to listOf("Madrid"),
        "Castilla-La Mancha" to listOf("Albacete", "Ciudad Real", "Cuenca", "Guadalajara", "Toledo"),
        "C. Valenciana" to listOf("Alicante", "Castellon", "Valencia"),
        "Extremadura" to listOf("Badajoz", "Caceres"),
        "Murcia" to listOf("Murcia"),
        "Andalucía" to listOf("Almeria", "Cadiz", "Cordoba", "Granada", "Huelva", "Jaen", "Malaga", "Sevilla"),
        "Baleares" to listOf("Islas Baleares"),
        "Canarias" to listOf("SC Tenerife", "Las Palmas")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProvinceSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RegionAdapter(regiones) { provincias ->
            val action = ProvinceSelectorFragmentDirections
                .actionProvinceSelectorToProvinceList(provincias.toTypedArray())
            binding.root.findNavController().navigate(action)
        }

        binding.recyclerRegiones.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerRegiones.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
