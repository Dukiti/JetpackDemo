package com.dungnm.example.core.mock

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Error(val errorCode: String, val errorMessage: String? = null) :
        NetworkResult<Nothing>()
}
