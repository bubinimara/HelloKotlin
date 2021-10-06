package com.example.hellokotlin.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistry
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

    // TODO: pass arguments with saved state - and try to  remove id param
    val movies = MutableLiveData<Resource<List<Movie>>>()
    val currentMovie = MutableLiveData<Resource<Movie>>()
    val ratingResult = MutableLiveData<Resource<Boolean>>()

    fun load(isRecreated: Boolean) {
        if(isRecreated)
            return

         viewModelScope.launch{
            movies.value = dataRepository.getMovies()
        }

    }

    fun getMovieById(id:Int){
        viewModelScope.launch {
            dataRepository.getMovieById(id).collect {
                currentMovie.value = it
            }
        }
    }

    fun rateMovie(movieId: Int,rate:Int) {
        viewModelScope.launch {
            Log.d("MYTAG", "rateMovie: $rate")
            ratingResult.value = dataRepository.rateMovie(movieId,rate)
        }
    }
}