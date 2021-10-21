package com.example.movieapp.di

import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.api.RetrofitRepositoryImplementation
import com.example.movieapp.api.RetrofitService
import com.example.movieapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun getMoviesData(): RetrofitService = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(
        RetrofitService::class.java)

    @Provides
    @Singleton
    fun signInRepository(apiService: RetrofitService): RetrofitRepository = RetrofitRepositoryImplementation(apiService)
}