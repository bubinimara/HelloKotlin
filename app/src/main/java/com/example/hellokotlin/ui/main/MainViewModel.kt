package com.example.hellokotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.Event
import com.example.hellokotlin.R
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Davide Parise
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    /**
     * Movies List
     */
    private val _movies = MutableLiveData<List<Movie>>()
    val movies:LiveData<List<Movie>> = _movies
  /*  val movies: MutableLiveData<Resource<List<Movie>>> by lazy {
        MutableLiveData<Resource<List<Movie>>>().also {
            viewModelScope.launch {
                repository.getMovies()
            }
        }
    }*/

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _eventLogout = MutableLiveData<Event<Unit>>()
    val eventLogout: LiveData<Event<Unit>> = _eventLogout

    private val _eventError = MutableLiveData<Event<Int>>()
    val eventError: MutableLiveData<Event<Int>> = _eventError

    fun load(){
        viewModelScope.launch {
            repository.getMovies().collect {resource->
                _isLoading.value = resource is Resource.Loading
                when(resource){
                    is Resource.Error -> _eventError.value = Event(R.string.error_unknow)
                    is Resource.Loading -> _movies.value = resource.data ?: emptyList()
                    is Resource.Success -> _movies.value = resource.data ?: emptyList()
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            //TODO:show DIALOG
            repository.logout().collect {
                _eventLogout.value = Event(Unit)
            }
        }
    }
}