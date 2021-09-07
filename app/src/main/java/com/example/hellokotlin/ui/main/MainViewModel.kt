package com.example.hellokotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User

/**
 * @author Davide Parise
 */
class MainViewModel : ViewModel() {

    private val repository = DataRepository()

    /**
     * User list
     */
    val users: MutableLiveData<Resource<List<User>>> by lazy {
        MutableLiveData<Resource<List<User>>>().also {
            repository.getLastActiveUsers()
        }
    }

    /**
     * Movies List
     */
    val movies: MutableLiveData<Resource<List<Movie>>> by lazy {
        MutableLiveData<Resource<List<Movie>>>().also {
            repository.getMovies()
        }
    }

    fun refresh(){
        repository.getLastActiveUsers().also {
            users.value = it
        }
        repository.getMovies().also {
            movies.value = it
        }
    }
}