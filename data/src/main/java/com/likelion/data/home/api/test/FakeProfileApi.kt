package com.likelion.data.home.api.test

import com.likelion.data.model.ProfileEntity

// data/api/FakeProfileApi.kt
// ProfileEntity를 반환하는 Fake API
class FakeProfileApi {
    fun getProfileEntity(): List<ProfileEntity> {
        return listOf(
            ProfileEntity(id = 1L, userId = 1L, drinkingCapacity = "LOW", religion = null, isSmoke = false, age = 25, nickname = "코딩러", introduce = "안녕하세요", contact = "sns", profileImage = "https://example.com/profile1.jpg", location = "Seoul", sex = "MALE"),
            ProfileEntity(id = 2L, userId = 2L, drinkingCapacity = null, religion = "CHRISTIAN", isSmoke = true, age = 28, nickname = "안드로이드", introduce = "반갑습니다", contact = null, profileImage = "https://example.com/profile2.jpg", location = "Busan", sex = "FEMALE")
        )
    }
}