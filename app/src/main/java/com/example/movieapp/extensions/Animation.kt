package com.example.movieapp.extensions

import android.view.View
import android.view.animation.AnimationUtils

fun View.animation(anim: Int) {
    val animation = AnimationUtils.loadAnimation(context, anim)
    startAnimation(animation)
}

