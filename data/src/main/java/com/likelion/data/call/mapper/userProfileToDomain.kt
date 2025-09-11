package com.likelion.data.call.mapper

import com.likelion.domain.call.model.UserProfileModel
import com.likelion.remote.model.response.UserProfileResponse

fun UserProfileResponse.toDomain(): UserProfileModel {
    return UserProfileModel(
        nickname = this.nickname,
        age = this.age,
        location = this.location?:"null",
        interests = this.interests?:emptyList(),
        profileImageUrl = this.profileImageUrl?:"http"
    )
}
