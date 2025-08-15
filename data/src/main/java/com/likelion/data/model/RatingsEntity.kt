package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class RatingsEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY

    @SerializedName("call_iid")
    val callId: Long,  // BigInt

    @SerializedName("score")
    val score: Int,  // Int, NOT NULL

    @SerializedName("comment")
    val comment: String?,  // VarChar(255), nullable

    @SerializedName("create_at")
    val createdAt: String // DateTime, NOT NULL, JSON에서는 ISO 8601 문자열로 받는 경우가 많음
)