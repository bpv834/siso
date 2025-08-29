package com.likelion.data.mypage.mapper

import com.likelion.data.mypage.enum_model.Religion
import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.mypage.model.UsersFullModel

fun UsersFullEntity.dataToDomain() : UsersFullModel
= this.run {
    UsersFullModel(
        id = userId,
        userImages = profileImage,
        location = location,
        nickname = nickname,
        age = age,
        sex = "MALE",
        preferenceSex = preferenceSex.dataToDomain(),
        voiceUrl = voiceUrl,
        drinkingCapacity = drinkingCapacity.dataToDomain(),
        interests = interest,
        introduce = introduce ?: "",
        religion = religion.dataToDomain(),
        isSmoke = when (isSmoke){
            true -> "흠연자"
            false -> "비흡연자"
        },
        mbti = mbti,
        meeting = meeting
    )

}