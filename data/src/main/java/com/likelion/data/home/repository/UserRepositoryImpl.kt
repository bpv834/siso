package com.likelion.data.home.repository

import com.likelion.data.home.mapper.toDomain
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.remote.api.MatchingApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val matchingApiService: MatchingApiService
) : UsersRepository {
    override suspend fun getAllUsers(eccessToken : String): Result<List<UsersModel>> {
        return runCatching {
            val matchingUsers = matchingApiService.getMatchingUsers(eccessToken)
            val usersModelList = matchingUsers.map { it.toDomain() }
            usersModelList
        }
    }


    override suspend fun getUserById(id: Long): UsersModel {
        val fakeUser = UsersModel(
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
            interests = listOf("음악감상", "사진촬형", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다."
        )
        return fakeUser
    }
}