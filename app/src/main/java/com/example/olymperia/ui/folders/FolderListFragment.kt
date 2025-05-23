package com.example.olymperia.ui.folders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.R
import com.example.olymperia.databinding.FragmentFolderListBinding
import com.example.olymperia.model.Folder
import com.example.olymperia.repository.PortRepository

class FolderListFragment : Fragment() {

    private var _binding: FragmentFolderListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val provinces = PortRepository.getAllProvinces()
        val folders = provinces.map { provinceName ->
            Folder(
                id = provinceName.hashCode().toLong(),
                name = provinceName,
                iconRes = R.drawable.ic_folder
            )
        }

        val adapter = FolderAdapter(folders) { folder ->
            val action = FolderListFragmentDirections.actionToFolderDetail(folder.name)
            findNavController().navigate(action)
        }

        binding.rvFolders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFolders.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
