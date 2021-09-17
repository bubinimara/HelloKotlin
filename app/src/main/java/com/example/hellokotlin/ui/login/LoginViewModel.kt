package com.example.hellokotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.util.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var dataRepository: DataRepository,private val appUtils: AppUtils) : ViewModel() {

    private val NO_LOGIN = false

    val data = MutableLiveData<Resource<User>>()
        .also {
            //it.value = dataRepository.login("username", "password")
            it.value = Resource.Loading()
            viewModelScope.launch {
                appUtils.initialize()
                dataRepository.loadLastSession().run {
                    it.value = this
                }
            }
        }

    fun login(username: String, password: String) {
        if(NO_LOGIN){
            data.value = Resource.Success(User(name = username))
            return
        }

        viewModelScope.launch {
            data.value = try {
                dataRepository.login(username, password)
            }catch (e:Exception){
                Resource.Error(400)
            }
        }
    }
}