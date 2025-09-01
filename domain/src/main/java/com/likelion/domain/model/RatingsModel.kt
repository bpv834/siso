package com.likelion.domain.model


data class RatingsModel(
    val id: Long,  // BigInt, PRIMARY KEY

    val callId: Long,  // BigInt

    val score: Int,  // Int, NOT NULL

    val comment: String?,  // VarChar(255), nullable

    val createdAt: String // DateTime, NOT NULL, JSON에서는 ISO 8601 문자열로 받는 경우가 많음
)