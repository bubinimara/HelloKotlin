package com.example.hellokotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Davide Parise
 */
@HiltViewModel
class MainViewModel @Inject constructor(val repository: DataRepository) : ViewModel() {

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