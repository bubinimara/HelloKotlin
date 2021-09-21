package com.example.hellokotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Davide Parise
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    /**
     * User list
     */
    val users: MutableLiveData<Resource<List<User>>> by lazy {
        MutableLiveData<Resource<List<User>>>().also {
            viewModelScope.launch {
                repository.getPopularUsers()
            }
        }
    }

    /**
     * Movies List
     */
    val movies: MutableLiveData<Resource<List<Movie>>> by lazy {
        MutableLiveData<Resource<List<Movie>>>().also {
            viewModelScope.launch {
                repository.getMovies()
            }
        }
    }

    val actionLogout = MutableLiveData<Resource<Boolean>>()


    fun refresh(){
        viewModelScope.launch {
            repository.getPopularUsers().also {
                users.value = it
            }
            repository.getMovies().also {
                movies.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            actionLogout.value = Resource.Success(true)
        }
    }
}