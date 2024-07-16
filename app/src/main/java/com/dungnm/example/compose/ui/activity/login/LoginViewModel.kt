package com.dungnm.example.compose.ui.activity.login

import androidx.lifecycle.MutableLiveData
import com.dungnm.example.compose.network.repo.ILoginRepo
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: ILoginRepo
) : BaseViewModel() {

    private val _loginResLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loginResLiveData: MutableLiveData<Boolean> = _loginResLiveData

    fun login(user: String?, pass: String?) = launch {
        delay(3000)
        val loginSuccess = loginRepo.login(user, pass)
        _loginResLiveData.postValue(loginSuccess)
    }

}