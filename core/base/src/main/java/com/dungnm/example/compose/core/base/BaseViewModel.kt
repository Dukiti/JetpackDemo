package com.dungnm.example.compose.core.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.core.Storage
import com.dungnm.example.compose.core.constants.Tags
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(false)
    val errCode: MutableLiveData<Error> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        val error = parseException(throwable)
        Log.e("base_log", "BaseViewModel_exceptionHandler: error : $error")
        onError(error)
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
            Log.e(
                "search23912868",
                "launchSilent : ${Thread.currentThread().name} - ${Thread.currentThread().id}"
            )
            try {
                block.invoke(mainScope)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    open fun onError(error: Error) {
        errCode.postValue(error)
    }

    private fun parseException(exception: Throwable): Error {
        return when (exception) {
            is SocketTimeoutException -> {
                Error.TimeOutError
            }

            is UnknownHostException -> {
                Error.UnknownError
            }

            is HttpException -> {
                parseHttpException(exception)
            }

            else -> {
                Error.UnknownError
            }
        }
    }

    private fun parseHttpException(exception: HttpException): Error {
        val code = exception.code()
        if (code == 503) {
            val dataErr = exception.response()?.errorBody()?.string()
            val response: ErrResponse? = Gson().fromJson(dataErr, ErrResponse::class.java)
            if (response == null) {
                return Error.ServerError("-1", null, null)
            } else {
                return Error.ServerError(response.code, response.des, dataErr)
            }
        } else {
            return Error.HttpError("$code")
        }
    }
}