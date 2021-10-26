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
 * Created by Davide Parise on 27/09/21.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(val dataRepository: DataRepository): ViewModel() {
    companion object{
        private const val TAG ="DetailViewModel"
    }

    // the list movies to show
    private val _movies = MutableLiveData<List<Movie>>()
    val movies:LiveData<List<Movie>> = _movies

    // when data is loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    // if an error occur
    private val _eventError = MutableLiveData<Event<Int>>()
    val eventError:LiveData<Event<Int>> =_eventError

    // which is the position of the current movie
    private val _eventSelectedMoviePosition = MutableLiveData<Event<Int>>()
    val eventSelectedMoviePosition:LiveData<Event<Int>> = _eventSelectedMoviePosition

    // load once - just at start up
    private var shouldLoad = true

    fun load(id:Int?) {
        if(!shouldLoad){
            // load once
            return
        }
        shouldLoad = false
        viewModelScope.launch{
            dataRepository.getMovies().collect {
                _isLoading.value = it is Resource.Loading // never load because it came from db!
                when(it){
                    is Resource.Error -> _eventError.value = Event(R.string.error_unknow)
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        onDataLoaded(it.data,id)
                    }
                }
            }
        }
    }

    private fun onDataLoaded(data: List<Movie>?, id: Int?) {
        val movies = data ?: emptyList()
        _movies.value = movies
        shouldLoad = false
        findItemPosition(movies, id)?.let {
            _eventSelectedMoviePosition.value = it
        }
    }

    private fun findItemPosition(movies: List<Movie>?, movieId: Int?): Event<Int>? {
        if(movies == null || movieId == null){
            return null
        }

        for (i in 0..movies.size ){
            if(movies[i].id == movieId)
                return Event(i)
        }

        return null
    }
}