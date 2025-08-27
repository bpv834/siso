package com.likelion.data.home.api.test

import com.likelion.data.model.UsersEntity

class FakeUserApi {
    fun getUsersEntity(): List<UsersEntity> {
        return listOf(
            UsersEntity(id = 1L, provider = "KAKAO", phoneNumber = "010-1234-5678", isOnline = true, notificationSubscribed = true, refreshToken = "token1", isBlock = false, isDeleted = false, createdAt = "", updatedAt = "", deletedAt = null),
            UsersEntity(id = 2L, provider = "Apple", phoneNumber = "010-9876-5432", isOnline = false, notificationSubscribed = true, refreshToken = "token2", isBlock = false, isDeleted = false, createdAt = "", updatedAt = "", deletedAt = null)
        )
    }
}