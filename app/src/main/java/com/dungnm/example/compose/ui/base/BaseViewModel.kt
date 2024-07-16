package com.dungnm.example.compose.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.utils.Storage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(false)
    val errCode: MutableLiveData<ErrorCode> = MutableLiveData()
    val currentTheme = MutableStateFlow(Tags.THEME_LIGHT)

    init {
        val theme =
            Storage.getInstance().getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        currentTheme.value = theme
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        val code = when (throwable) {
            is SocketTimeoutException -> {
                ErrorCode.TIME_OUT
            }

            is UnknownHostException -> {
                ErrorCode.UNKNOWN_HOST
            }

            else -> {
                ErrorCode.COMMON_ERR
            }
        }
        errCode.postValue(code)
        Log.e("2139728", "exceptionHandler: ${code.name}")
    }

    val mainScope = viewModelScope.plus(exceptionHandler)

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return mainScope.launch {
            isLoading.postValue(true)
            try {
                block.invoke(mainScope)
            } finally {
                isLoading.postValue(false)
            }
            return@launch
        }
    }

    fun launchSilent(block: suspend CoroutineScope.() -> Unit): Job {
        return mainScope.launch {
            try {
                block.invoke(mainScope)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun updateTheme() {
        val theme =
            Storage.getInstance().getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        if (theme != currentTheme.value) {
            currentTheme.value = theme
        }
    }
}