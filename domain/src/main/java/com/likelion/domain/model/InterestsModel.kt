package com.likelion.domain.model


data class InterestsModel(
    val id: String,  // VARCHAR(255), PRIMARY KEY, Auto Increment 문자열

    val userId: Long,  // BigInt, NOT NULL

    val interest: String  // VarChar(40), NOT NULL
)