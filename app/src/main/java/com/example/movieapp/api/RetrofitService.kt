package com.example.movieapp.api

import com.example.movieapp.models.Detail
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page")
        page: Int
    ): Response<Movie>

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("page")
        page: Int
    ): Response<Movie>

    @GET("{movie_id}")
    suspend fun getDetail(
        @Path("movie_id")
        movieId: Int,
    ): Response<Detail>
}