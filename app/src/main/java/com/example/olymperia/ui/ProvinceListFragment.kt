package com.example.olymperia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.olymperia.databinding.FragmentProvinceListBinding
import com.example.olymperia.databinding.ItemProvinciaBinding

class ProvinceListFragment : Fragment() {

    private var _binding: FragmentProvinceListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProvinceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provincias = arguments?.getStringArray("provincias")?.toList() ?: emptyList()

        adapter = ProvinceAdapter(provincias) { provinciaSeleccionada ->
            val action = ProvinceListFragmentDirections
                .actionProvinceListFragmentToPortListFragment(arrayOf(provinciaSeleccionada))
            findNavController().navigate(action)
        }

        binding.recyclerProvincias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProvincias.adapter = adapter
        binding.btnVolverInicio.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
