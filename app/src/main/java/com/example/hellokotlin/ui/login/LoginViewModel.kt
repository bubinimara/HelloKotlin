package com.example.hellokotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User

class LoginViewModel : ViewModel() {

    var dataRepository:DataRepository = DataRepository()

    val data = MutableLiveData<Resource<User>>()
        .also {
            it.value = dataRepository.login("username", "password")
        }

    fun login(username: String, password: String) {
        data.value = dataRepository.login(username, password)
    }
}