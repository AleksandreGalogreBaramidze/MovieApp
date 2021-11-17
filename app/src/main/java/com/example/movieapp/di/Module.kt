package com.example.movieapp.di


import com.example.movieapp.api.RetrofitService
import com.example.movieapp.interceptor.QueryInterceptor
import com.example.movieapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient()).addConverterFactory(createGson()).build()

    @Provides
    @Singleton
    fun createGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit):RetrofitService = retrofit.create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(QueryInterceptor())
        .build()
}