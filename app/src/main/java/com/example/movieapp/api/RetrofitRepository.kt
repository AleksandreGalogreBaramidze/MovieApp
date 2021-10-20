package com.example.movieapp.api

import com.example.movieapp.models.Movie

interface RetrofitRepository {
    suspend fun getPopularMoviesData(): ApiErrorHandling<Movie>
    suspend fun getTopMoviesData(): ApiErrorHandling<Movie>
}