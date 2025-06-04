package com.example.olymperia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentRankingBinding
import com.example.olymperia.model.RankingUser
import com.example.olymperia.ui.adapters.RankingAdapter
import com.example.olymperia.ui.dialogs.FriendSummaryDialogFragment

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private lateinit var adapter: RankingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val usuarioNivel = 34
        val usuarioPuntos = 1420
        val usuarioDivision = 5

        binding.tvDivision.text = "División $usuarioDivision"
        binding.tvNivel.text = "Nivel $usuarioNivel"
        binding.tvPuntos.text = "$usuarioPuntos puntos"

        val amigos = listOf(
            RankingUser("Carlos", 40, 2000),
            RankingUser("Lucía", 30, 1700),
            RankingUser("Andrés", 25, 1400)
        )

        adapter = RankingAdapter(amigos)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }
}
