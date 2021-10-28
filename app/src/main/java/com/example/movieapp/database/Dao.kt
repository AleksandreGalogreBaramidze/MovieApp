package com.example.movieapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface Dao {
    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun getFavMovies(): List<Entity>

    @Insert
    fun favMovie(moviesEntity : Entity)

    @Query("DELETE FROM movies WHERE id=:id")
    fun removeFavMovie(id: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :id)")
    fun checker(id: Int): Boolean
}