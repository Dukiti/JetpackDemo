package com.dungnm.example.mapper

import com.google.gson.Gson

inline fun <reified T> Any?.mapObject(proceed: (result: T) -> Unit): T {
    val result = this.mapObject<T>()
    proceed.invoke(result)
    return result
}

inline fun <reified T> Any?.mapObject(): T {
    val gson = Gson()
    val data = gson.toJson(this)
    val result = gson.fromJson(data, T::class.java)
    return result
}