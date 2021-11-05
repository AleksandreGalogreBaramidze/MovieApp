package com.example.movieapp.api

import com.example.movieapp.models.Detail
import com.example.movieapp.models.Movie
import com.google.gson.Gson
import javax.inject.Inject

class RetrofitRepositoryImplementation @Inject constructor(private val ApiService: RetrofitService) : RetrofitRepository  {

    override suspend fun getPopularMoviesData(page:Int): ApiErrorHandling<Movie> {
        return try {
            val response = ApiService.getPopularMovies(page = page)
            if (response.isSuccessful) {
                val body = response.body()!!
                ApiErrorHandling.success(body)
            } else {
                val errorModel =
                    Gson().fromJson(response.errorBody()!!.toString(), Error::class.java)
                ApiErrorHandling.error(errorModel.message)
            }
        } catch (e: Exception) {
            ApiErrorHandling.error(e.message.toString())
        }
    }

    override suspend fun getTopMoviesData(page:Int): ApiErrorHandling<Movie> {
        return try {
            val response = ApiService.getTopRatedMovies(page = page)
            if (response.isSuccessful) {
                val body = response.body()!!
                ApiErrorHandling.success(body)
            } else {
                val errorModel = Gson().fromJson(response.errorBody()!!.toString(), Error::class.java)
                ApiErrorHandling.error(errorModel.message)

            }
        } catch (e: Exception) {
            ApiErrorHandling.error(e.message.toString())
        }
    }


    override suspend fun getDetailMovie(id: Int): ApiErrorHandling<Detail> {
        return try {
            val response = ApiService.getDetail(movieId = id)
            if (response.isSuccessful) {
                val body = response.body()!!
                ApiErrorHandling.success(body)
            } else {
                val errorModel = Gson().fromJson(response.errorBody()!!.toString(), Error::class.java)
                ApiErrorHandling.error(errorModel.message)
            }
        } catch (e: Exception) {
            ApiErrorHandling.error(e.message.toString())
        }
    }


}