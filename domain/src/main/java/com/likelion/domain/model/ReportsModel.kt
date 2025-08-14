package com.likelion.domain.model


data class ReportsModel(
    val id: Long,  // PRIMARY KEY, Auto Increment

    val callId: Long,  // BigInt, NOT NULL

    val reporter: Long,  // PRIMARY, NOT NULL

    val reported: Long,  // PRIMARY, NOT NULL

    val reportTitle: String,  // VarChar(50), NOT NULL

    val description: String,  // Text, NOT NULL

    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val reportedStatus: String  // Enum, NOT NULL (Pending, Reviewing, Actioned, Rejected)
)