package com.dungnm.example.compose.core.base

import com.google.gson.annotations.SerializedName

data class ErrResponse(
    @SerializedName("code") val code : String,
    @SerializedName("des") val des : String?,
    @SerializedName("data") val data : String?,
)