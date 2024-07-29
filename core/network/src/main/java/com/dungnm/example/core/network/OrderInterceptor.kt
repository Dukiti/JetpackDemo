package com.dungnm.example.core.network

import androidx.annotation.IntRange
import okhttp3.Interceptor

abstract class OrderInterceptor(@IntRange(from = MIN_ODER, to = MAX_ORDER) val order: Int): Interceptor {

    companion object {
        const val MIN_ODER = 0L
        const val MAX_ORDER = 9999L
    }
}

class DefaultInterceptor : OrderInterceptor(0) {
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())
}