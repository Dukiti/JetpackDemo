package com.dungnm.example.compose.network.mock

import android.util.Log

class MockFactory {
    companion object {
        var instance: MockFactory? = null
        fun g(): MockFactory {
            if (instance == null) {
                instance = MockFactory()
            }
            return instance!!
        }
    }

    private val mapService = mutableMapOf<String, IServiceMock>()

    fun putPock(service: IServiceMock): MockFactory {
        mapService[service::class.java.name] = service
        return this
    }

    fun getMock(path: String): IServiceMock? {
        Log.e("123123", "getMock: $path")
        mapService.forEach { item ->
            if (item.value.valid(path)) {
                return item.value
            }
        }
        return null
    }
}