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
import com.example.hellokotlin.ui.util.RateUtil
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
    companion object{
        private const val TAG = "RateDialogViewModel"
    }
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
                _showProgress.value = it is Resource.Loading
                when(it){
                    is Resource.Error -> _showError.value = Event(R.string.error_unknow)
                    is Resource.Loading -> {} // never called
                    is Resource.Success ->{
                        movie = it.data
                        val rated:Float? = it.data?.accountState?.rate
                        val converted = RateUtil.convertRateForApp(rated)
                        _rate.value = converted
                        Log.d(TAG, "load: rated $rated converted $converted ")
                        _showError.value = Event(R.string.empty_text)
                    }
                }
            }
        }
    }

    private val _enableRateButton = MutableLiveData<Boolean>()

    fun onRateChanged(rate: Float){
        _enableRateButton.value = rate > 0
    }

    fun rateMovie(rate: Float){
        if(movie == null) {
            _showError.value = Event(R.string.error_no_movie_selected)
            return
        }

        val convertedRate = RateUtil.convertRateForWeb(rate)
        Log.d(TAG, "rateMovie: rate $rate converted $convertedRate")
        if(convertedRate <= 0F){ // check converted rate
/*
            _showError.value = Event(R.string.error_not_rated)
            return
*/
        }
        _showError.value = Event(R.string.empty_text)
        viewModelScope.launch {
            repository.rateMovie(movie!!, convertedRate).collect {
                _showProgress.value = it is Resource.Loading
                when(it){
                    is Resource.Error -> _showError.value = Event(R.string.error_unknow)//todo: add error mapper
                    is Resource.Loading -> _showProgress.value = true
                    is Resource.Success -> _closeDialog.value = Event(Unit)
                }

            }
        }
    }
}