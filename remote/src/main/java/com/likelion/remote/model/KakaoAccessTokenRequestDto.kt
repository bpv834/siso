package com.likelion.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoAccessTokenRequestDto(
    val accessToken: String,
)