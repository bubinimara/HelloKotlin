package com.example.hellokotlin.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellokotlin.Event
import com.example.hellokotlin.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogLogoutViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    // Event logout
    private val _eventLogout = MutableLiveData<Event<Unit>>()
    val eventLogout: LiveData<Event<Unit>> = _eventLogout

    fun logout() {
        viewModelScope.launch {
            repository.logout().collect {
                _eventLogout.value = Event(Unit)
            }
        }
    }
}