package com.juanimozo.recipesrandomizer.presentation.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun getImage(
    url: String,
    view: ImageView,
    context: Context
) {

    return Picasso.with(context)
        .load(url)
        .into(view)
}