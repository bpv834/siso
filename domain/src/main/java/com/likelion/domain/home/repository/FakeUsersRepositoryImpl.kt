package com.likelion.domain.home.repository

import com.likelion.domain.home.model.UsersModel

class FakeUsersRepositoryImpl : UsersRepository {
    override suspend fun getAllUsers(): List<UsersModel> {
        return fakeUsers.toList()
    }

    override suspend fun getUserById(id: Long): UsersModel {
        return fakeUser
    }

    private val fakeUser = UsersModel(
        id = 4L,
        isOnline = true,
        userImages = listOf(
            "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
        ),
        location = "America",
        nickname = "코딩러",
        age = 65,
        voiceUrl = "https://example.com/voice1.mp3",
        interests = listOf("풋볼", "영화", "음악"),
        introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다."
    )

    private val fakeUsers = mutableListOf(
        UsersModel(
            id = 1L,
            isOnline = true,
            userImages = listOf(
                "https://m.health.chosun.com/site/data/img_dir/2024/10/22/2024102202299_0.jpg",
                "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
            ),
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
            userImages = listOf("https://m.health.chosun.com/site/data/img_dir/2022/12/16/2022121601634_0.jpg"),
            location = "Busan",
            nickname = "안드로이드",
            age = 28,
            voiceUrl = "https://example.com/voice2.mp3",
            interests = listOf("운동", "여행"),
            introduce = "새로운 것을 배우는 것을 좋아합니다."
        ),
        UsersModel(
            id = 3L,
            isOnline = false,
            userImages = listOf("https://m.health.chosun.com/site/data/img_dir/2025/08/12/2025081202707_0.jpg"),
            location = "DongDuCheon",
            nickname = "휴머노이드",
            age = 230,
            voiceUrl = "https://example.com/voice2.mp3",
            interests = listOf("운동", "여행"),
            introduce = "새로운 것을 배우는 것을 좋아합니다."
        )
    )


}