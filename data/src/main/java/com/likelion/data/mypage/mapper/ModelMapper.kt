package com.likelion.data.mypage.mapper

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
        preferenceSex = TODO(),
        voiceUrl = voiceUrl,
        drinkingCapacity = drinkingCapacity.dataToDomain(),
        interests = TODO(),
        introduce = TODO(),
        religion = TODO(),
        isSmoke = TODO(),
        mbti = TODO(),
        meeting = TODO()
    )

}