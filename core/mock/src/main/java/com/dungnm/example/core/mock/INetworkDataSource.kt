package com.dungnm.example.core.mock

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

interface INetworkDataSource {
    suspend fun <T> postRequest(
        urlEnd: String? = null,
        body: ModelReq? = null,
        headers: Map<String, String>? = null,
        typeOfT: Type
    ): NetworkResult<T>
}

inline fun <reified T> getTypeOfT(): Type =
    TypeToken.getParameterized(T::class.java).type

inline fun <reified T> getTypeArrayListOfT(): Type =
    TypeToken.getParameterized(ArrayList::class.java,
        TypeToken.getParameterized(T::class.java).type).type


