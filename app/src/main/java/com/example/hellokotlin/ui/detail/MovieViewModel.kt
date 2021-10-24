package com.example.hellokotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.Event
import com.example.hellokotlin.R
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *
 * Created by Davide Parise on 24/10/21.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: DataRepository):ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie:LiveData<Movie> = _movie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _eventError = MutableLiveData<Event<Int>>()
    val eventError:LiveData<Event<Int>> = _eventError

    fun load(movieId:Int?){
        if (movieId == null)throw Exception("MovieId cannot be null")

        viewModelScope.launch {
            repository.getMovieById(movieId).collect {res->
                _isLoading.value = res is Resource.Loading
                when(res){
                    is Resource.Error -> _eventError.value = Event(R.string.error_unknow)
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        res.data?.let {
                            _movie.value = it
                        }
                    }
                }
            }
        }
    }
}