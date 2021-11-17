package com.example.movieapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <F> Fragment.observer(liveData: LiveData<F>, observer: (it: F) -> Unit) {
    liveData.observe(this.viewLifecycleOwner, { observer(it) })
}