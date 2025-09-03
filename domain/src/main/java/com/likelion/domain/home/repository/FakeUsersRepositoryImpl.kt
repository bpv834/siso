package com.likelion.domain.home.repository

import com.likelion.domain.home.model.UsersModel

class FakeUsersRepositoryImpl : UsersRepository {
    override suspend fun getAllUsers(eccessToken : String): Result<List<UsersModel> >{
        return Result.success(fakeUsers)
    }

    override suspend fun getUserById(id: Long): UsersModel {
        return fakeUser
    }

    private val fakeUser = UsersModel(
        id = 4L,
        userImages = listOf(

        ),
        location = "America",
        nickname = "코딩러",
        age = 65,
        voiceUrl = "https://example.com/voice1.mp3",
        interests = listOf("풋볼", "영화", "음악"),
        introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다.",
        presentStatus = com.likelion.domain.enums.PresentStatus.IN_CALL

    )

    private val fakeUsers = mutableListOf(
        UsersModel(
            id = 1L,
            userImages = listOf(

            ),
            location = "Seoul",
            nickname = "코딩러",
            age = 25,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다.",
            presentStatus = com.likelion.domain.enums.PresentStatus.IN_CALL


        ),
        UsersModel(
            id = 2L,
            userImages = listOf(),
            location = "Busan",
            nickname = "안드로이드",
            age = 28,
            voiceUrl = "https://example.com/voice2.mp3",
            interests = listOf("운동", "여행"),
            introduce = "새로운 것을 배우는 것을 좋아합니다.",
            presentStatus = com.likelion.domain.enums.PresentStatus.IN_CALL

        ),
        UsersModel(
            id = 3L,
            userImages = listOf(),
            location = "DongDuCheon",
            nickname = "휴머노이드",
            age = 230,
            voiceUrl = "https://example.com/voice2.mp3",
            interests = listOf("운동", "여행"),
            introduce = "새로운 것을 배우는 것을 좋아합니다.",
            presentStatus = com.likelion.domain.enums.PresentStatus.IN_CALL

        )
    )


}