package com.example.movieapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias openNextPage = (page: Int) -> Unit
typealias getId = (id: Int) -> Unit


