package com.likelion.domain.model


data class ProfileModel(
    val id: Long,  // PRIMARY KEY, 숫자형

    val userId: Long,  // BigInt

    val drinkingCapacity: String?, // Enum (nullable)

    val religion: String?, // Enum (nullable)

    val isSmoke: Boolean?, // Boolean nullable

    val age: Int, // Int, NOT NULL

    val nickname: String, // Varchar(50), NOT NULL

    val introduce: String?, // Varchar(255), nullable

    val contact: String?, // Enum, nullable

    val profileImage: String, // URL, NOT NULL

    val location: String, // Enum, NOT NULL

    val sex: String // Enum, NOT NULL
)