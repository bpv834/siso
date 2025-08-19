package com.likelion.remote.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenPairDto(
    val accessToken: String,
    val refreshToken: String
)
