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

    private val _movies = MutableLiveData<List<Movie>>()
    val movies:LiveData<List<Movie>> = _movies

    // when data is loading ... view should show progress
    private val _isLoadin = MutableLiveData<Boolean>()
    val isLoadin:LiveData<Boolean> = _isLoadin

    private val _eventError = MutableLiveData<Event<Int>>()
    val eventError:LiveData<Event<Int>> =_eventError

    private val _eventSelectedMoviePosition = MutableLiveData<Event<Int>>()
    val eventSelectedMoviePosition:LiveData<Event<Int>> = _eventSelectedMoviePosition

    private var shouldLoad = true


    fun load(id:Int?) {
         viewModelScope.launch{
             dataRepository.getMovies().collect {
                 _isLoadin.value = it is Resource.Loading // never load because it came from db!
                 when(it){
                     is Resource.Error -> _eventError.value = Event(R.string.error_unknow)
                     is Resource.Loading -> {}
                     is Resource.Success -> {
                         it.data?.let { movies ->
                             onDataLoaded(movies, id)
                         }
                     }
                 }
            }
        }
    }

    private fun onDataLoaded(data: List<Movie>, id: Int?) {
        if(shouldLoad) {
            _movies.value = data // todo: check why it work??
            shouldLoad = false
            findItemPosition(data, id)?.let {
                _eventSelectedMoviePosition.value = it
            }
        }
    }

    private fun findItemPosition(movies: List<Movie>, movieId: Int?): Event<Int>? {
        movieId?.let {
            for (i in 0..movies.size ){
                if(movies[i].id == movieId)
                    return Event(i)
            }
        }
        return null
    }
}