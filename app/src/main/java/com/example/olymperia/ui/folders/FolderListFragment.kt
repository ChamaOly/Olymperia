package com.example.olymperia.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.PortAdapter
import com.example.olymperia.databinding.FragmentPortListBinding
import com.example.olymperia.repository.PortRepository

import com.example.olymperia.R

class FolderListFragment : Fragment() {

    private lateinit var binding: FragmentPortListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val provinciasFiltradas = arguments?.getStringArray("provincias") ?: emptyArray()
        val todosLosPuertos = PortRepository.getAllSegments()

        val puertosFiltrados = if (provinciasFiltradas.isNotEmpty()) {
            todosLosPuertos.filter { it.province in provinciasFiltradas }
        } else {
            todosLosPuertos
        }

        val adapter = PortAdapter(puertosFiltrados, { puerto ->

            val action = PortListFragmentDirections
                .actionPortListFragmentToPortDetailFragment(puerto.province)
            findNavController().navigate(action)
        }, requireContext())

        binding.recyclerProvinces.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProvinces.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_port_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_back_to_regions -> {
                findNavController().navigate(R.id.nav_ports)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
