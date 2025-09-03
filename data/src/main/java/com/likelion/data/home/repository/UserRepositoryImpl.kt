package com.likelion.data.home.repository

import com.likelion.data.home.mapper.toDomain
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.remote.api.MatchingApiService
import com.likelion.domain.enums.PresentStatus
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val matchingApiService: MatchingApiService
) : UsersRepository {
    override suspend fun getAllUsers(eccessToken : String): Result<List<UsersModel>> {
        return runCatching {
            val matchingUsers = matchingApiService.getMatchingUsers("Bearer $eccessToken")
            val usersModelList = matchingUsers.map { it.toDomain() }
            usersModelList
        }
    }


    override suspend fun getUserById(id: Long): UsersModel {
        val fakeUser = UsersModel(
            id = 4L,
            userImages = listOf(),
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("풋볼", "영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다.",
            presentStatus = PresentStatus.ONLINE

        )
        return fakeUser
    }
}