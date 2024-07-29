package com.dungnm.example.core.network

import com.google.gson.Gson
import com.google.gson.JsonObject

open class ModelReq {

    fun toJson() =  Gson().toJsonTree(this) as JsonObject

}