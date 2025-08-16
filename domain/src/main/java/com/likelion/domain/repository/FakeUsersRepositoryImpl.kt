package com.likelion.domain.repository

import com.likelion.domain.model.UsersModel

class FakeUsersRepositoryImpl : UsersRepository {
    private val fakeUsers = mutableListOf(
        UsersModel(
            id = 1L,
            isOnline = true,
            userImages = listOf("https://example.com/user1_img1.jpg", "https://example.com/user1_img2.jpg"),
            location = "Seoul",
            nickname = "코딩러",
            age = 25,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다."
        ),
        UsersModel(
            id = 2L,
            isOnline = false,
            userImages = listOf("https://example.com/user2_img1.jpg"),
            location = "Busan",
            nickname = "안드로이드",
            age = 28,
            voiceUrl = "https://example.com/voice2.mp3",
            interests = listOf("운동", "여행"),
            introduce = "새로운 것을 배우는 것을 좋아합니다."
        )
    )

    override suspend fun getAllUsers(): List<UsersModel> {
        return fakeUsers.toList()
    }
}