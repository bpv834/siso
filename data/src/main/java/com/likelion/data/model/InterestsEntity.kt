package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class InterestsEntity(
    @SerializedName("id")
    val id: String,  // VARCHAR(255), PRIMARY KEY, Auto Increment 문자열

    @SerializedName("user_id")
    val userId: Long,  // BigInt, NOT NULL

    @SerializedName("interest")
    val interest: String  // VarChar(40), NOT NULL
)