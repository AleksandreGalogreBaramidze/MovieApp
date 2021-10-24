package com.example.movieapp.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movieapp.R

fun ImageView.load(context: Context, url: String) {
    Glide.with(context).load(url).placeholder(R.drawable.movie_frame)
        .into(this)
}