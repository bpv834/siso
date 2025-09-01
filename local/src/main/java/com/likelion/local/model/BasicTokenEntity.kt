package com.likelion.local.model

data class BasicTokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val status: String,
    val hasProfile: Boolean
)