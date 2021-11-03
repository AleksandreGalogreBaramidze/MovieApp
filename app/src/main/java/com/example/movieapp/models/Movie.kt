package com.example.movieapp.models

data class Movie(
    val page: Int?,
    val results: List<Results>?
    ){
    data class Results(
        val backdrop_path: String = "unknown",
        val id: Int= 1,
        val original_title: String = "unknown",
        val overview: String = "unknown",
        val popularity: Double = 0.0,
        val poster_path: String = "unknown",
        val release_date: String = "unknown",
        val title: String = "unknown",
        val video: Boolean? = false,
        val vote_average: Double = 0.0,
    ){
        fun urlGenerator(): String{
            return "https://image.tmdb.org/t/p/w500/$backdrop_path"
        }
    }
}