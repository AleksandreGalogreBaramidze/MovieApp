package com.example.movieapp.models

data class Movie(
    val page: Int?,
    val results: List<Result>?
    ){
    data class Result(
        val backdrop_path: String?,
        val id: Int?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String,
        val release_date: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
    )
}