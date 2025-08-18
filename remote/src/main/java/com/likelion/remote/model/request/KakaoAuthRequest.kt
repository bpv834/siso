package com.likelion.remote.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoAuthRequest(
    val accessToken: String,
    val codeVerifier: String
)
