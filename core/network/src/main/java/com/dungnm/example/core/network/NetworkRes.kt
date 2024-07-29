package com.dungnm.example.core.network

import com.google.gson.annotations.SerializedName

data class NetworkRes<T>(
    @SerializedName(value = "status", alternate = ["code", "errorcode"])
    val status: String = "",
    @SerializedName("message", alternate = ["errordesc"])
    val message: String? = null,
    @SerializedName("data")
    private val data: T? = null,
    private val isSuccess: Boolean = false
)
