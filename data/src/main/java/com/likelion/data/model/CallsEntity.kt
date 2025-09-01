package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class CallsEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY

    @SerializedName("match_id")
    val matchId: Long,  // BigInt

    @SerializedName("status")
    val status: String,  // Enum, NOT NULL

    @SerializedName("duration")
    val duration: Int,  // Int, NOT NULL

    @SerializedName("started_at")
    val startedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("ended_at")
    val endedAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)