package com.dungnm.example.compose.network.mock

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        val service: IServiceMock? = MockFactory.g().getMock(url)
        return service?.process(context, request)!!
    }
}