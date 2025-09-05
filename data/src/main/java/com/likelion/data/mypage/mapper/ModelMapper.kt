package com.likelion.data.mypage.mapper


import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.mypage.model.UsersFullModel

import kotlin.collections.map

fun UsersFullEntity.dataToDomain() : UsersFullModel
= this.run {
    UsersFullModel(
        id = id,
        userImages = profileImage,
        location = location,
        nickname = nickname,
        age = age,
        sex = sex.description,
        preferenceSex = preferenceSex.description,
        voiceUrl = voiceUrl,
        drinkingCapacity = drinkingCapacity.description,
        interests = interest.map { "#${it.description}" },
        introduce = introduce ?: "",
        religion = religion.description,
        isSmoke = when (isSmoke){
            true -> "흡연자"
            false -> "비흡연자"
        },
        mbti = mbti.value,
        meeting = meeting.map { "#${it.meeting}" },
    )

}