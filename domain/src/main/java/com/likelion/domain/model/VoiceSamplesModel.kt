package com.likelion.domain.model



data class VoiceSamplesModel(
    val id: Long,  // BIGINT, PRIMARY KEY, Auto Increment

    val userId: Long,  // BigInt, NOT NULL

    val url: String?,  // VarChar(255), nullable

    val duration: Int?,  // Int, nullable

    val fileSize: Int?,  // Int, nullable, 바이트 단위

    val createdAt: String?,  // DateTime, nullable, ISO 8601 문자열

    val updatedAt: String?  // DateTime, nullable, ISO 8601 문자열
)