package com.juanimozo.recipesrandomizer.presentation.util

import android.view.View
import com.airbnb.lottie.LottieAnimationView

class SetAnimation {

    // Set and start animation
    fun startAnimation(
        view: LottieAnimationView,
        animation: Int
    ) {
        view.setAnimation(animation)
        view.playAnimation()
    }

    // Stops animation and hide view
    fun finishAnimation(
        view: LottieAnimationView
    ) {
        view.pauseAnimation()
        view.visibility = View.GONE
    }
}