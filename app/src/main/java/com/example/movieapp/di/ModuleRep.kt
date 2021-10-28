package com.example.movieapp.di

import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.api.RetrofitRepositoryImplementation
import com.example.movieapp.api.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleRep {
    @Provides
    @Singleton
    fun signInRepository(
        retrofitService: RetrofitService,
    ): RetrofitRepository = RetrofitRepositoryImplementation(retrofitService)
}