package com.likelion.domain.mypage.model

data class UserEditImageModel(
    val userId: Long,  // BigInt, NOT NULL

    val path: String?,  // VarChar(255), nullable

    val serverImageName: String,  // VarChar(255), NOT NULL

    val originalName: String,  // VarChar(255), NOT NULL
)