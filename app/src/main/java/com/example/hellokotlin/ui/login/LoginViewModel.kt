package com.example.hellokotlin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.Event
import com.example.hellokotlin.R
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var dataRepository: DataRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _eventError = MutableLiveData<Event<Int>>()
    val eventError:LiveData<Event<Int>> = _eventError

    private val _eventLoggedIn = MutableLiveData<Event<Unit>>()
    val eventLoggedIn:LiveData<Event<Unit>> = _eventLoggedIn

    fun load(){
        viewModelScope.launch {
            dataRepository.loadLastSession().collect() {res->
                hanldeResponse(res)
            }
        }
    }

    private fun hanldeResponse(res: Resource<User>) {
        //_isLoading.value = res is Resource.Loading
        when (res) {
            is Resource.Error -> {
                // todo: add a mapper for erros and move error code too
                var resId = R.string.error_unknow
                if (res.error == Resource.Error.ErrorCode.INVALID_USERNAME_OR_PASSWORD){
                    resId = R.string.invalid_username_password
                }
                _eventError.value = Event(resId)
                _isLoading.value = false
            }
            is Resource.Loading -> {
                _isLoading.value = true
            }
            is Resource.Success -> {
                // not change loading state
                if(res.data != null) _eventLoggedIn.value = Event(Unit)
                else _isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            dataRepository.login(username, password).collect {
                hanldeResponse(it)
            }
        }
    }
}