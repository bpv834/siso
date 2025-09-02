package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SisoResponse<T>(
    val data: T?,
    val errorMessage: String?
)