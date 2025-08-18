package com.likelion.domain.login.model

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)