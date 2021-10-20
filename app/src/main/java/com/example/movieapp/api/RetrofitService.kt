package com.example.movieapp.api

import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        api_Key: String = KEY,
    ): Response<Movie>

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        api_Key: String = KEY,
    ): Response<Movie>
}