package com.example.olymperia.ui
import com.google.firebase.database.FirebaseDatabase

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olymperia.databinding.FragmentPortListBinding
import com.example.olymperia.model.PortSegment
import com.example.olymperia.repository.PortRepository
import com.example.olymperia.PortAdapter
import com.example.olymperia.ui.HonorUnlockedDialog
import com.example.olymperia.utils.ScoreManager
import com.example.olymperia.network.StravaApi
import com.example.olymperia.R
import com.example.olymperia.utils.AchievementManager
import com.example.olymperia.utils.AnimationUtils
import com.example.olymperia.utils.AnimationUtils.animarDesbloqueoDeSello
import com.example.olymperia.utils.UserProgressManager
import kotlinx.coroutines.launch

class PortListFragment : Fragment() {
    private var puertoActual: PortSegment? = null

    private var _binding: FragmentPortListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PortAdapter
    private lateinit var ports: List<PortSegment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provincias = arguments?.getStringArray("provincias") ?: emptyArray()
        ports = PortRepository.getAllSegments().filter { it.province in provincias }

        adapter = PortAdapter(ports, { port ->
            val prefs = requireContext().getSharedPreferences("strava_prefs", 0)
            val token = prefs.getString("access_token", null)
            val athleteId = prefs.getLong("athlete_id", -1L)
            val expiresAt = prefs.getLong("expires_at", 0L)
            val currentTime = System.currentTimeMillis() / 1000
            val isTokenValid = !token.isNullOrEmpty() && currentTime < expiresAt && athleteId != -1L
            Log.d("DEBUG_TOKEN_CHECK", "Token=$token, athleteId=$athleteId, expiresAt=$expiresAt, now=${System.currentTimeMillis() / 1000}")

            if (isTokenValid) {
                val strava = StravaApi.create(token!!)

                lifecycleScope.launch {
                    try {
                        val efforts = strava.getSegmentEfforts(
                            auth = "Bearer $token",
                            segmentId = port.id,
                            athleteId = athleteId
                        )

                        val resultado = ScoreManager.procesarEsfuerzosStrava(
                            requireContext(),
                            port.id,
                            efforts.size,
                            port.points
                        ) { honor ->
                            Toast.makeText(requireContext(), "🏆 Desbloqueado: ${honor.nombre}", Toast.LENGTH_LONG).show()
                            HonorUnlockedDialog(honor) {
                                Log.d("HONOR", "Honor desbloqueado: ${honor.nombre}")
                            }.show(parentFragmentManager, "honor_dialog")
                        }

                        if (!resultado.isNullOrEmpty()) {
                            UserProgressManager.addCompletedSegment(requireContext(), port.id)

                            val firebaseDb = FirebaseDatabase.getInstance("https://olymperia-default-rtdb.europe-west1.firebasedatabase.app")
                            val firebaseRef = firebaseDb.getReference("usuarios").child(athleteId.toString())
                            firebaseRef.child("puertosCompletados").push().setValue(port.name)

                            val nuevosLogros = AchievementManager.checkAndUnlockAchievements(requireContext())
                            if (nuevosLogros.isNotEmpty()) {
                                AchievementDisplay.showAchievementDialog(requireContext(), nuevosLogros.first())
                            }
                        }

                        val tv = binding.tvResultadoPuntos
                        if (!resultado.isNullOrEmpty()) {
                            tv.text = "🏆 $resultado"
                        } else {
                            tv.text = "✅ Ya has registrado todos los esfuerzos de Strava."
                        }

                        tv.scaleX = 0.8f
                        tv.scaleY = 0.8f
                        tv.alpha = 0f
                        tv.visibility = View.VISIBLE

                        tv.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .alpha(1f)
                            .setDuration(300)
                            .start()

                        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.honor_unlocked)
                        mediaPlayer?.apply {
                            setOnCompletionListener { player -> player.release() }
                            start()
                        }

                        adapter.notifyItemChanged(ports.indexOf(port))
                        binding.btnVolverInicio.setOnClickListener {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }

                    } catch (e: Exception) {
                        Log.e("STRAVA_ERROR", "Error consultando esfuerzos", e)
                        Toast.makeText(requireContext(), "Error al consultar Strava", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Falta acceso válido a Strava", Toast.LENGTH_SHORT).show()
            }
        }, requireContext())

        binding.recyclerProvinces.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProvinces.adapter = adapter
        binding.btnVolverInicio.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tvResultadoPuntos.setOnClickListener {
            it.visibility = View.GONE

            val destinoView = adapter.ultimaVistaSello
            val iconRes = adapter.ultimoIcono

            if (destinoView != null && iconRes != 0) {
                animarDesbloqueoDeSello(requireActivity(), iconRes, destinoView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
