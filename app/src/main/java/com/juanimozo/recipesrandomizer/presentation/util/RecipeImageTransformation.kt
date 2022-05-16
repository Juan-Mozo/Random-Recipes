package com.juanimozo.recipesrandomizer.presentation.util

import android.content.Context
import android.widget.ImageView
import com.juanimozo.recipesrandomizer.R
import com.squareup.picasso.Picasso

fun getImage(
    url: String,
    view: ImageView,
    context: Context
) {
    if (url.isEmpty()) {
        view.setImageResource(R.drawable.image_not_found)
    } else {
        return Picasso
            .with(context)
            .load(url)
            .into(view)
    }
}