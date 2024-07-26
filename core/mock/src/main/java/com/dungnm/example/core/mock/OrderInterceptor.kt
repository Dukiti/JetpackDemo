package com.dungnm.example.core.mock

import okhttp3.Interceptor

abstract class OrderInterceptor(val order: Int): Interceptor {

    init {
        require(order in MIN_ODER..MAX_ORDER) {
            "Interceptor Order must be between $MIN_ODER and $MAX_ORDER"
        }
    }
    companion object {
        const val MIN_ODER = 0
        const val MAX_ORDER = 9999
    }
}

class DefaultInterceptor : OrderInterceptor(MIN_ODER) {
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())
}