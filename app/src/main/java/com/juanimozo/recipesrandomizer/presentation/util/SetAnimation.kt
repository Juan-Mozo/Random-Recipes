package com.juanimozo.recipesrandomizer.presentation.util

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class SetAnimation(
    private val view: LottieAnimationView,
    private val animation: Int
) {

    // Set and start animation
    fun startAnimation() {
        view.setAnimation(animation)
        view.playAnimation()
    }

    // Stops animation and hide view
    fun finishAnimation() {
        view.pauseAnimation()
        view.visibility = View.GONE
    }

    fun setFrame(frame: Int) {
        view.setAnimation(animation)
        view.frame = frame
    }


}