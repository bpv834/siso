package com.likelion.local.model

data class BasicTokenEntity(
    val refreshToken: String,
    val status: String,
    val hasProfile: Boolean
)