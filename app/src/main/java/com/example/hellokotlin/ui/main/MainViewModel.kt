package com.example.hellokotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User

class MainViewModel : ViewModel() {

    var dataRepository:DataRepository = DataRepository()

    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            it.value = User("default")

        }
    }

    fun switchUser(username: String) {
        var  resource = dataRepository.login(username,username)
        when(resource){
            is Resource.Success -> user.value = resource.data
        }
    }

    fun login(username: String, password: String) {
        dataRepository.login(username,password)
    }
}