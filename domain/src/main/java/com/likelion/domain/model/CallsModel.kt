package com.likelion.domain.model


data class CallsModel(
    val id: Long,  // BigInt, PRIMARY KEY

    val matchId: Long,  // BigInt

    val status: String,  // Enum, NOT NULL

    val duration: Int,  // Int, NOT NULL

    val startedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val endedAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)