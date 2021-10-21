package com.example.movieapp.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.TOP_RATED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) : ViewModel() {
    private var _livedata = MutableLiveData<ApiErrorHandling<Movie>>()
    var livedata: LiveData<ApiErrorHandling<Movie>> = _livedata
    var category = POPULAR

    private fun popularMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = retrofitRepository.getPopularMoviesData()
                _livedata.postValue(result)
            }
        }
    }

    private fun topRatedMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = retrofitRepository.getTopMoviesData()
                _livedata.postValue(result)
            }
        }
    }

    fun getData() {
        if (category == POPULAR) {
            popularMovies()
        } else if (category == TOP_RATED) {
            topRatedMovies()
        }
    }
}