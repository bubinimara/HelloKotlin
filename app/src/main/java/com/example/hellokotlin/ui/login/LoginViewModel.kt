package com.example.hellokotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var dataRepository: DataRepository) : ViewModel() {

    val data = MutableLiveData<Resource<User>>()
        .also {
            it.value = Resource.Loading()
            viewModelScope.launch {
                dataRepository.loadLastSession().collect() {user->
                    it.value = user
                }
            }
        }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            dataRepository.login(username, password).collect {
                data.value = it
            }
        }
    }
}