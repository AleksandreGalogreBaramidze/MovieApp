package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.database.Dao
import com.example.movieapp.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Application): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "saved_movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(moviesDataBase: Database): Dao {
        return moviesDataBase.getInfo()
    }
}