package com.dungnm.example.compose.core.base

sealed class Error {
    class HttpError(val code: String) : Error()
    class ServerError(val code: String, val message: String?, val data: String? = null) : Error()
    data object TimeOutError : Error()
    data object UnknownError : Error()
}