package com.example.movieapp.models

data class MovieDetails(
    val backdrop_path: String = "unknown",
    val id: Int= 1,
    val original_title: String = "",
    val overview: String = "unknown",
    val popularity: Double = 0.0,
    val poster_path: String = "unknown",
    val release_date: String = "unknown",
    val title: String = "",
    val video: Boolean? = false,
    val vote_average: Double = 0.0,
){
    fun urlGenerator(): String{
        return "https://image.tmdb.org/t/p/w500/$backdrop_path"
    }
}