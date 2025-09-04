package com.likelion.remote.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInterestResponseDto  (
    val success: Boolean,
    val data : List<Interest>,
    val errorMessage: String?
)

@JsonClass(generateAdapter = true)
data class Interest  (
    val interest : String,
)