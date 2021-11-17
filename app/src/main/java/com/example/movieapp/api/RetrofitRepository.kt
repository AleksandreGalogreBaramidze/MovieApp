package com.example.movieapp.api

import com.example.movieapp.models.Detail
import com.example.movieapp.models.Movie

interface RetrofitRepository {
    suspend fun getPopularMoviesData(page: Int): ApiErrorHandling<Movie>
    suspend fun getTopMoviesData(page: Int): ApiErrorHandling<Movie>
    suspend fun getDetailMovie(id:Int): ApiErrorHandling<Detail>
}