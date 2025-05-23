package com.example.olymperia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentPortListBinding
import com.example.olymperia.repository.PortRepository

class PortListFragment : Fragment() {

    private lateinit var binding: FragmentPortListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val provincias = PortRepository.getAllProvinces()

        val adapter = ProvinceAdapter(provincias) { provincia ->
            val action = PortListFragmentDirections
                .actionPortListFragmentToPortDetailFragment(provincia)
            findNavController().navigate(action)
        }

        binding.recyclerProvinces.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProvinces.adapter = adapter
    }
}
