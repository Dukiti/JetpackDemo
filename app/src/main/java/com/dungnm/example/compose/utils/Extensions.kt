package com.dungnm.example.compose.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <T> LiveData<T>.awaitValue(): T? {
    return suspendCoroutine { cont ->
        val observer = object : Observer<T?> {
            override fun onChanged(t: T?) {
                removeObserver(this)
                cont.resume(t)
            }
        }
        observeForever(observer)
    }
}