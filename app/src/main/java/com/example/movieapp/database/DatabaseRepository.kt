package com.example.movieapp.database

import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: Dao){

    fun getSavedMovies(): List<Entity> {
        return dao.getFavMovies()
    }

    fun saveMovie(moviesEntity: Entity) {
        return dao.favMovie(moviesEntity)
    }

    fun checker(id: Int): Boolean {
        return dao.checker(id)
    }

    fun deleteMovie(id: Int) {
        dao.removeFavMovie(id)
    }

}