package com.dungnm.example.compose.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.proceed(chain.request())
        return Response.Builder()
            .code(503)
            .message("test")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(ResponseBody.create("application/json".toMediaType(), "{\"code\" : \"10\",\"des\" : \"Test Error\",\"code_detail\" : \"10_adb\"}"))
            .addHeader("content-type", "application/json")
            .build()
    }
}