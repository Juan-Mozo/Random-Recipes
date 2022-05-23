package com.juanimozo.recipesrandomizer.presentation.util

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.delay

class SetAnimation(
    val animation: LottieAnimationView,
    private val animationFile: Int
) {

    // Set and start animation
    fun startAnimation() {
        animation.setAnimation(animationFile)
        animation.playAnimation()
    }

    // Stops animation and hide view
    fun finishAnimation() {
        animation.cancelAnimation()
        animation.visibility = View.GONE
    }

    suspend fun likeAnimation(icon: LottieAnimationView, frame: Int) {
        // Hide icon and show animation
        icon.visibility = View.INVISIBLE
        animation.visibility = View.VISIBLE

        // Start animation and wait until it finishes
        startAnimation()
        delay(500)

        // Finish Animation and set Icon
        finishAnimation()
        icon.frame = frame
        icon.visibility = View.VISIBLE

    }

    fun setFrame(frame: Int) {
        animation.setAnimation(animationFile)
        animation.frame = frame
    }


}