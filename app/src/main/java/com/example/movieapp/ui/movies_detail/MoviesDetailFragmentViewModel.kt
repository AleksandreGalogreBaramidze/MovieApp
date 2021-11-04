package com.example.movieapp.ui.movies_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.api.RetrofitRepository
import com.example.movieapp.database.DatabaseRepository
import com.example.movieapp.database.Entity
import com.example.movieapp.models.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MoviesDetailFragmentViewModel@Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private var _liveData = MutableLiveData<ApiErrorHandling<Detail>>()
    var liveData: LiveData<ApiErrorHandling<Detail>> = _liveData
    private var _checkerLiveData = MutableLiveData<Boolean>()
    var checkerLiveData: LiveData<Boolean> = _checkerLiveData

    fun movieDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = retrofitRepository.getDetailMovie(id)
                _liveData.postValue(result)
            }
        }
    }

    fun favoriteMovies(Entity: Entity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.saveMovie(Entity)
            }
        }
    }

    fun isFavorite(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _checkerLiveData.postValue(databaseRepository.checker(id))
            }
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.deleteMovie(id)
            }

        }
    }

}