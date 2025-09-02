package com.likelion.data.home.mapper

import com.likelion.domain.home.model.UsersModel
import com.likelion.remote.model.response.MatchingUserResponse
import com.likelion.domain.enums.PresentStatus
import com.likelion.remote.model.enums.RemotePresentStatus

fun MatchingUserResponse.toDomain(): UsersModel = UsersModel(
    id = this.userId.toLong(),
    userImages = this.imageUrls,
    location = this.location ?: "알 수 없음",
    nickname = this.nickname,
    age = this.age,
    voiceUrl = this.voiceSampleUrl ?: "",
    interests = this.interests,
    introduce = this.introduce ?: "자기소개가 없습니다.",
    presentStatus = when (this.presenseStatus) {
        RemotePresentStatus.ONLINE -> {
            PresentStatus.ONLINE
        }

        RemotePresentStatus.OFFLINE -> {
            PresentStatus.OFFLINE
        }

        RemotePresentStatus.IN_CALL -> {
            PresentStatus.IN_CALL
        }
    }

)