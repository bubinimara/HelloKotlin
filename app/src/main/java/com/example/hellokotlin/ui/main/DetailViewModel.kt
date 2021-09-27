package com.example.hellokotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *
 * Created by Davide Parise on 27/09/21.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(val dataRepository: DataRepository): ViewModel() {

    val movies = MutableLiveData<Resource<List<Movie>>>()

    fun getMovies(){
         viewModelScope.launch{
            movies.value = dataRepository.getMovies()
        }
    }

}