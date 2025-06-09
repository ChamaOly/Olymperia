package com.example.olymperia.ui

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
// NUEVO IMPORTANTE
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


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
                Log.d("DEBUG_TOKEN", "Token: $token, AthleteId: $athleteId, ExpiresAt: $expiresAt, Now: ${System.currentTimeMillis() / 1000}")

                lifecycleScope.launch {
                    try {
                        val efforts = strava.getSegmentEfforts(
                            auth = "Bearer $token",
                            segmentId = port.id,
                            athleteId = athleteId
                        )

                        Log.d("EFFORTS_DEBUG", "Segment ${port.id} efforts = ${efforts.size}")

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
                            // Registrar el puerto completado
                            val completions = efforts.size
                            val segmentPoints = port.points * completions
                            val prefs = requireContext().getSharedPreferences("strava_prefs", 0)
                            val name = prefs.getString("athlete_name", "Desconocido")
                            val avatar = prefs.getString("avatar_url", null)

// Recalcular total actualizado (usando ScoreManager si lo mantienes temporalmente)
                            val updatedTotalPoints = ScoreManager.getTotalPoints(requireContext())
                            val updatedLevel = maxOf(1, updatedTotalPoints / 200)

                            val firebase = FirebaseFirestore.getInstance()
                            val userRef =
                                firebase.collection("users").document(athleteId.toString())

                            val firebaseUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid

                            val updates = mapOf(
                                "firebaseUid" to firebaseUid,
                                "segments.${port.id}.completions" to completions,
                                "segments.${port.id}.points" to segmentPoints,
                                "totalPoints" to updatedTotalPoints,
                                "level" to updatedLevel,
                                "name" to name,
                                "avatarUrl" to avatar,
                                "last_updated" to System.currentTimeMillis()
                            )

                            userRef.set(updates, SetOptions.merge())


                            userRef.set(updates, SetOptions.merge())
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

                        // Eliminado temporizador de cierre automático: el usuario debe tocar para cerrarlo

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