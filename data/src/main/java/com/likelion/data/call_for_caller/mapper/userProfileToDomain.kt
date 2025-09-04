package com.likelion.data.call_for_caller.mapper

import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.remote.model.response.UserProfile

fun UserProfile.toDomain(): UserProfileModel {
    return UserProfileModel(
        nickname = this.nickname,
        age = this.age,
        location = this.location?:"null",
        interests = this.interests?:emptyList(),
        profileImageUrl = this.profileImageUrl
    )
}