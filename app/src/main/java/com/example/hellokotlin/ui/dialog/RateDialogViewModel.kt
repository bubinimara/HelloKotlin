package com.example.hellokotlin.ui.dialog

import android.util.Log
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
 * Created by Davide Parise on 18/10/21.
 * TODO:add savedstatehandler with id
 */
@HiltViewModel
class RateDialogViewModel @Inject constructor(private val repository: DataRepository): ViewModel() {
    // if show progress or not
    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showProgress:LiveData<Boolean> = _showProgress
    // the amount of stars to show
    private val _rate:MutableLiveData<Float> = MutableLiveData()
    val rate:LiveData<Float> = _rate
    // If an error occur
    private val _showError:MutableLiveData<Event<Int>> = MutableLiveData()
    val showError:LiveData<Event<Int>> = _showError

    // Event to close dialog
    private val _closeDialog:MutableLiveData<Event<Unit>> = MutableLiveData()
    val closeDialog:LiveData<Event<Unit>> = _closeDialog

    private var movie:Movie? = null

    fun load(id:Int){
        viewModelScope.launch {
            repository.getMovieById(id).collect {
                when(it){
                    is Resource.Error -> _showProgress.value = false
                    is Resource.Loading -> _showProgress.value = true // never called
                    is Resource.Success ->{
                        movie = it.data
                        val rated:Int = it.data?.accountState?.rate ?: -1
                        _rate.value = if(rated in 0..10)(rated/2.0).toFloat() else 0.0F
                        _showProgress.value = false
                    }
                }
            }
        }
    }

    fun rateMovie(id: Int, rate: Float){
        if(id<0) {
            _showError.value = Event(R.string.error_no_movie_selected)
            return
        }
        if(rate <= 0){
            _showError.value = Event(R.string.error_not_rated)
            return
        }
        viewModelScope.launch {
            val r = (rate*2).toInt() //todo:add better conversion
            Log.d("MYTAG", "rateMovie: $rate and r = $r ")
            repository.rateMovie(movie!!, r).collect {
                when(it){
                    is Resource.Error -> _showError.value = Event(R.string.error_unknow)//todo: add error mapper
                    is Resource.Loading -> _showProgress.value = true
                    is Resource.Success -> _closeDialog.value = Event(Unit)
                }

            }
        }
    }
}