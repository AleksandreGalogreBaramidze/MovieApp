package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    )