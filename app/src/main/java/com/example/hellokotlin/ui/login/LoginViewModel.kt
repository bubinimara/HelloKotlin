package com.example.hellokotlin.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel : ViewModel() {

    var dataRepository: DataRepository = DataRepositoryImpl()

    val data = MutableLiveData<Resource<User>>()
        .also {
            //it.value = dataRepository.login("username", "password")
        }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            data.value = try {
                dataRepository.login(username, password)
            }catch (e:Exception){
                Resource.Error(400)
            }
        }
    }
}