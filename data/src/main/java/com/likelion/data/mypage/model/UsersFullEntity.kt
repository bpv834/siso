package com.likelion.data.mypage.model

import com.google.gson.annotations.SerializedName
import com.likelion.data.mypage.enum_model.DrinkingCapacity
import com.likelion.data.mypage.enum_model.PreferenceSex
import com.likelion.data.mypage.enum_model.Religion

data class UsersFullEntity (
    @SerializedName("id")
    val id: Long,  // PRIMARY KEY, 숫자형

    @SerializedName("user_id")
    val userId: Long,  // BigInt

    @SerializedName("age")
    val age: Int, // Int, NOT NULL

    @SerializedName("nickname")
    val nickname: String, // Varchar(50), NOT NULL

    @SerializedName("voice_url")
    val voiceUrl: String, //  NOT NULL

    @SerializedName("introduce")
    val introduce: String?, // Varchar(255), nullable

    @SerializedName("profile_Image")
    val profileImage: String, // URL, NOT NULL

    @SerializedName("location")
    val location: String, // Enum, NOT NULL

    @SerializedName("sex")
    val sex: String, // Boolean, NOT NULL

    @SerializedName("preference_sex")
    val preferenceSex : PreferenceSex,

    @SerializedName("is_smoke")
    val isSmoke: Boolean, // Enum (nullable)

    @SerializedName("drinking_capacity")
    val drinkingCapacity: DrinkingCapacity, // Enum (nullable)

    @SerializedName("religion")
    val religion: Religion, // Enum (nullable)

    @SerializedName("mbti")
    val mbti: String, // Enum (nullable)

    @SerializedName("interest")
    val interest : List<String>,

    @SerializedName("meeting")
    val meeting : List<String>,
)