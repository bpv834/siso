package com.likelion.data.mypage.model

import androidx.annotation.Size
import com.google.gson.annotations.SerializedName
import com.likelion.util.DrinkingCapacity
import com.likelion.util.Interest
import com.likelion.util.Mbti
import com.likelion.util.Meeting
import com.likelion.util.PreferenceSex
import com.likelion.util.Religion
import com.likelion.util.Sex

data class UsersFullEntity (
    @SerializedName("id")
    val id: Long,  // PRIMARY KEY, 숫자형

    @SerializedName("user_id")
    val userId: String,  // BigInt

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
    val sex: Sex, // Boolean, NOT NULL

    @SerializedName("preference_sex")
    val preferenceSex: PreferenceSex,

    @SerializedName("is_smoke")
    val isSmoke: Boolean, // Enum (nullable)

    @SerializedName("drinking_capacity")
    val drinkingCapacity: DrinkingCapacity, // Enum (nullable)

    @SerializedName("religion")
    val religion: Religion, // Enum (nullable)

    @SerializedName("mbti")
    val mbti: Mbti, // Enum (nullable)

    @SerializedName("interest")
    val interest: List<Interest>,

    @SerializedName("meeting")
    @Size(min =3,max = 7)
    val meeting: List<Meeting>,
)