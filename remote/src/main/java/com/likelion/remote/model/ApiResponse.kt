package com.likelion.remote.model

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val errorMessage: String?,
)