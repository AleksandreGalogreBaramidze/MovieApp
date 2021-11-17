package com.example.movieapp.extensions

import android.content.Context
import android.widget.Toast

fun String.toast(context: Context) {
    return Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}