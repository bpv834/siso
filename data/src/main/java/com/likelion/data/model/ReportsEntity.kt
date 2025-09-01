package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class ReportsEntity(
    @SerializedName("id")
    val id: Long,  // PRIMARY KEY, Auto Increment

    @SerializedName("call_id")
    val callId: Long,  // BigInt, NOT NULL

    @SerializedName("reporter")
    val reporter: Long,  // PRIMARY, NOT NULL

    @SerializedName("reported")
    val reported: Long,  // PRIMARY, NOT NULL

    @SerializedName("report_title")
    val reportTitle: String,  // VarChar(50), NOT NULL

    @SerializedName("description")
    val description: String,  // Text, NOT NULL

    @SerializedName("created_at")
    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("reported_status")
    val reportedStatus: String  // Enum, NOT NULL (Pending, Reviewing, Actioned, Rejected)
)