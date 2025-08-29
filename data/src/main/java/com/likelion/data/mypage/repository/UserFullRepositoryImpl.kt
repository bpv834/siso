package com.likelion.data.mypage.repository

import com.likelion.data.home.api.test.FakeImagesApi
import com.likelion.data.home.api.test.FakeInterestApi
import com.likelion.data.home.api.test.FakeProfileApi
import com.likelion.data.home.api.test.FakeUserApi
import com.likelion.data.home.api.test.FakeVoiceApi
import com.likelion.data.mypage.enum_model.DrinkingCapacity
import com.likelion.data.mypage.enum_model.PreferenceSex
import com.likelion.data.mypage.enum_model.Religion
import com.likelion.data.mypage.mapper.dataToDomain
import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.repository.UserFullRepository
import javax.inject.Inject

class UserFullRepositoryImpl @Inject constructor(
//    private val userApi: FakeUserApi,
//    private val profileApi: FakeProfileApi,
//    private val imagesApi: FakeImagesApi,
//    private val voiceApi: FakeVoiceApi, // 🎤 음성 API 추가
//    private val interestApi: FakeInterestApi // ❤️ 관심사 API 추가
) : UserFullRepository {

    override suspend fun getUserById(id: Long): UsersFullModel {
        val fakeEntity = UsersFullEntity(
            id = 4L,
            userId = 12L,
            profileImage = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interest = listOf("풋볼", "영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다.", // null ?: ""
            drinkingCapacity = DrinkingCapacity.Never,
            religion = Religion.Christianity,
            isSmoke = false,
            sex = "FEMALE",
            preferenceSex = PreferenceSex.Female,
            mbti = "istj",
            meeting = listOf(),
        )

        val fakeUser = fakeEntity.dataToDomain()
        return fakeUser
    }
}