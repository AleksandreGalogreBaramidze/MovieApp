package com.example.movieapp.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.database.DatabaseRepository
import com.example.movieapp.database.Entity
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.SAVED
import com.example.movieapp.utils.Constants.TOP_RATED
import com.exaple.movieapp.movies.FlavorVersions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository, private val databaseRepository: DatabaseRepository, private val flavorVersions: FlavorVersions) : ViewModel() {
    private var _livedata = MutableLiveData<ApiErrorHandling<Movie>>()
    var livedata: LiveData<ApiErrorHandling<Movie>> = _livedata
    var category = flavorVersions.mainCategory
    private var _savedLiveData = MutableLiveData<List<Entity>>()
    var savedLiveData: LiveData<List<Entity>> = _savedLiveData
    private var _paginationLiveData = MutableLiveData<ApiErrorHandling<Movie>>()
    var paginationLiveData: LiveData<ApiErrorHandling<Movie>> = _paginationLiveData

    private fun popularMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = retrofitRepository.getPopularMoviesData(1)
                _livedata.postValue(result)
            }
        }
    }

    private fun topRatedMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = retrofitRepository.getTopMoviesData(1)
                _livedata.postValue(result)
            }
        }
    }

    private fun savedMovies(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = databaseRepository.getSavedMovies()
                _savedLiveData.postValue(result)
            }
        }
    }

    fun getData() {
        if (category == POPULAR) {
            popularMovies()
        } else if (category == TOP_RATED) {
            topRatedMovies()
        } else if (category == SAVED){
            savedMovies()
        }
    }

    fun loadNextPage(page: Int) {
        if(category == POPULAR){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val result = retrofitRepository.getPopularMoviesData(page)
                    _paginationLiveData.postValue(result)
                }
            }
        }else{
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val result = retrofitRepository.getTopMoviesData(page)
                    _paginationLiveData.postValue(result)
                }
            }
        }
    }
}