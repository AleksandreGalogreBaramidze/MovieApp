package com.example.movieapp.extensions

import android.app.Dialog
import android.view.Window
import androidx.viewbinding.ViewBinding

fun Dialog.setUp(height: Int, width:Int,binding: ViewBinding) {
    val parameters = window!!.attributes
    window!!.requestFeature(Window.FEATURE_NO_TITLE)
    window!!.setBackgroundDrawableResource(android.R.color.transparent)
    parameters.width = width
    parameters.height = height
    setContentView(binding.root)
}