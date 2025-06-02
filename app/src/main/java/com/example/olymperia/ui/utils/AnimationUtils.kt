package com.example.olymperia.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat

object AnimationUtils {

    fun animarDesbloqueoDeSello(
        activity: Activity,
        selloResId: Int,
        destinoView: View
    ) {
        destinoView.visibility = View.INVISIBLE

        val sello = ImageView(activity)
        sello.setImageResource(selloResId)
        val size = 128
        val layoutParams = FrameLayout.LayoutParams(size, size)
        sello.layoutParams = layoutParams
        sello.scaleType = ImageView.ScaleType.FIT_CENTER

        val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
        val overlay = FrameLayout(activity)
        overlay.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        ViewCompat.setTranslationZ(overlay, 100f)
        overlay.addView(sello)
        contentView.addView(overlay)

        overlay.post {
            val overlayPos = IntArray(2)
            overlay.getLocationOnScreen(overlayPos)

            val targetPos = IntArray(2)
            destinoView.getLocationOnScreen(targetPos)

            val startX = overlay.width / 2 - size / 2
            val startY = overlay.height / 2 - size / 2
            val endX = targetPos[0] - overlayPos[0]
            val endY = targetPos[1] - overlayPos[1]

            Log.d("ANIM", "INICIO → overlay size: ${overlay.width}x${overlay.height}")
            Log.d("ANIM", "startX=$startX startY=$startY → endX=$endX endY=$endY")

            sello.x = startX.toFloat()
            sello.y = startY.toFloat()

            val moverX = ObjectAnimator.ofFloat(sello, View.X, endX.toFloat())
            val moverY = ObjectAnimator.ofFloat(sello, View.Y, endY.toFloat())
            val rotar = ObjectAnimator.ofFloat(sello, View.ROTATION, 0f, 720f)
            val fadeOut = ObjectAnimator.ofFloat(sello, View.ALPHA, 1f, 0f)

            val animSet = AnimatorSet()
            animSet.playTogether(moverX, moverY, rotar, fadeOut)
            animSet.duration = 1200
            animSet.interpolator = AccelerateDecelerateInterpolator()
            animSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    contentView.removeView(overlay)
                    destinoView.visibility = View.VISIBLE
                }
            })

            Log.d("ANIM", "Ejecutando animación desde $startX,$startY hasta $endX,$endY")
            animSet.start()
        }
    }
}
