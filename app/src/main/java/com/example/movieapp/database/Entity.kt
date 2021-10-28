package com.example.movieapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class Entity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val poster: String,
    val title: String
    )

