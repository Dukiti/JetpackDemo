package com.dungnm.example.core.mock


data class NetworkConfig constructor(
    val debugMode: Boolean = false,
    val timeOutInMiniSecond: Long = 3000,
    val maxRequestPerHost: Int = 7,
    val baseUrl: String = "",
    val retryOnConnectFail: Boolean = false,
    val netWorkCodeSuccess: List<String> = listOf("0"),
    val useMockData: Boolean = false,
)

