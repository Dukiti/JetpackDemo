package com.dungnm.example.compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _loginResLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loginResLiveData: MutableLiveData<Boolean> = _loginResLiveData

    fun login(user: String?, pass: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                delay(3000)
                if (user == "admin" && pass == "admin") {
                    _loginResLiveData.postValue(true)
                } else {
                    _loginResLiveData.postValue(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.postValue(false)
            }
        }
    }

}