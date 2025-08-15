package com.likelion.domain.model

data class ImagesModel(
    val id: Long,  // PRIMARY KEY, Auto Increment

    val userId: Long,  // BigInt, NOT NULL

    val path: String?,  // VarChar(255), nullable

    val serverImageName: String,  // VarChar(255), NOT NULL

    val originalName: String,  // VarChar(255), NOT NULL

    val createdAt: String?,  // DateTime, nullable, ISO 8601 문자열

    val updatedAt: String  // DateTime, NOT NULL, ISO 8601 문자열
)