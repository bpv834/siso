package com.likelion.data.mypage.model

import com.google.gson.annotations.SerializedName

data class UsersFullEntity (
    @SerializedName("id")
    val id: Long,  // PRIMARY KEY, 숫자형

    @SerializedName("user_id")
    val userId: Long,  // BigInt

    @SerializedName("drinking_capacity")
    val drinkingCapacity: String?, // Enum (nullable)

    @SerializedName("religion")
    val religion: String?, // Enum (nullable)

    @SerializedName("is_smoke")
    val isSmoke: String?, // Enum (nullable)

    @SerializedName("age")
    val age: Int, // Int, NOT NULL

    @SerializedName("nickname")
    val nickname: String, // Varchar(50), NOT NULL

    @SerializedName("introduce")
    val introduce: String?, // Varchar(255), nullable

    @SerializedName("contact")
    val contact: String?, // Enum, nullable

    @SerializedName("profile_Image")
    val profileImage: String, // URL, NOT NULL

    @SerializedName("location")
    val location: String, // Enum, NOT NULL

    @SerializedName("sex")
    val sex: String, // Enum, NOT NULL

    @SerializedName("preference_sex")
    val preferenceSex : String,
)