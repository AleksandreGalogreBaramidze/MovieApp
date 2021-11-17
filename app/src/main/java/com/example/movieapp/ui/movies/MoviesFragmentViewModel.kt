package com.example.movieapp.ui.movies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.database.DatabaseRepository
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.utils.Constants.ONE_PAGE
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.SAVED
import com.example.movieapp.utils.Constants.TOP_RATED
import com.example.movieapp.utils.InternetChecker
import com.exaple.movieapp.movies.FlavorVersions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository, private val databaseRepository: DatabaseRepository, flavorVersions: FlavorVersions,@ApplicationContext context: Context) : ViewModel() {

    var category = flavorVersions.mainCategory

    private var _livedata = MutableLiveData<ApiErrorHandling<Movie>>()
    var livedata: LiveData<ApiErrorHandling<Movie>> = _livedata
    private var _savedLiveData = MutableLiveData<MutableList<MovieDetails>>()
    var savedLiveData: LiveData<MutableList<MovieDetails>> = _savedLiveData
    private var _isTopRated = MutableLiveData<Boolean>()
    var isTopRated : LiveData<Boolean> = _isTopRated
    private val _isNetworkAvailable = MutableLiveData<Boolean?>(null)
    val isNetworkAvailable: LiveData<Boolean?> = _isNetworkAvailable
    val checkConnection = InternetChecker(context)

    var newPage = 1

    private fun popularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitRepository.getPopularMoviesData(page)
            _livedata.postValue(result)
            _isTopRated.postValue(false)
        }
    }

    private fun topRatedMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitRepository.getTopMoviesData(page)
            _livedata.postValue(result)
            _isTopRated.postValue(true)
        }
    }

    private fun savedMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = databaseRepository.getSavedMovies()
            val favoriteMovies: MutableList<MovieDetails> = mutableListOf()
            result.forEach {
                favoriteMovies.add(MovieDetails(it.poster, it.id))
            }
            _savedLiveData.postValue(favoriteMovies)
        }
    }

    fun getData() {
        when (category) {
            POPULAR -> {
                popularMovies(ONE_PAGE)
            }
            TOP_RATED -> {
                topRatedMovies(ONE_PAGE)
            }
            SAVED -> {
                savedMovies()
            }
        }
    }

    fun loadNextPage() {
        if(category == POPULAR){
            popularMovies(newPage + ONE_PAGE)
        }else if(category == TOP_RATED){
            topRatedMovies(newPage + ONE_PAGE)
        }
    }

    fun setInternetConnection(networkStatus: Boolean?) =
        viewModelScope.launch {
            _isNetworkAvailable.value = networkStatus
        }

}