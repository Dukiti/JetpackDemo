package com.dungnm.example.compose.network.mock

import android.content.Context
import okhttp3.Request
import okhttp3.Response

interface IServiceMock {
    val endPointList: List<String>
    fun process(context: Context, request: Request): Response?
    fun valid(url: String?): Boolean {
        if (url.isNullOrEmpty()) {
            return false
        }
        endPointList.forEach {
            if (url.contains(it)) {
                return true
            }
        }
        return false
    }
}