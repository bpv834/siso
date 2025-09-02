package com.likelion.data.mypage.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.likelion.data.mypage.enum_model.DrinkingCapacity
import com.likelion.data.mypage.enum_model.MBTI
import com.likelion.data.mypage.enum_model.Meeting
import com.likelion.data.mypage.enum_model.PreferenceSex
import com.likelion.data.mypage.enum_model.Religion
import com.likelion.data.mypage.enum_model.Sex
import com.likelion.data.mypage.mapper.dataToDomain
import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.repository.UserFullRepository
import com.likelion.remote.api.UserApiService
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UserFullRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
) : UserFullRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getUserById(
        accessToken: String
    ): UsersFullModel {
        val tempAccess = "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoia2c4NDgwQGdtYWlsLmNvbSIsImlhdCI6MTc1Njc4MDM2MSwiZXhwIjoxNzU2Nzg3NTYxfQ.8IBIoCgIdmDiDLdaWPg8Q4NRNcCEnYQ5QiGXBaVdL6s"
        try {
            val user = userApiService.getUserId(tempAccess)
        } catch (e: IOException) {
            // 네트워크 문제 (인터넷 끊김 등)
            Timber.e(e, "네트워크 오류 발생")
            throw Exception("네트워크 오류: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // HTTP 프로토콜 에러
            Timber.e(e, "HTTP 오류 발생")
            throw Exception("서버 오류: ${e}")
        } catch (e: Exception) {
            // 그 외 예외
            Timber.e(e, "예상치 못한 오류 발생")
            throw Exception("예기치 못한 오류: ${e.localizedMessage}")
        }
        val fakeEntity = UsersFullEntity(
            id = 4L,
            userId = 12L,
            profileImage = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://samplelib.com/lib/preview/mp3/sample-15s.mp3",
            interest = listOf("#음악감상", "#영화감상", "#노래부르기"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다.", // null ?: ""
            drinkingCapacity = DrinkingCapacity.Never,
            religion = Religion.Christianity,
            isSmoke = false,
            sex = Sex.Female,
            preferenceSex = PreferenceSex.Female,
            mbti = MBTI.INTJ,
            meeting = listOf(
                Meeting.CLUB_ACTIVITY,
                Meeting.VOLUNTEER_ACTIVITY,
                Meeting.HOBBY_GROUP,
                Meeting.CULTURE_LIFE,
                Meeting.TOGETHER_SPORTS,
                Meeting.HIKING,
                Meeting.FOOD_TRIP,
            ),
        )

        val fakeUser = fakeEntity.dataToDomain()
        return fakeUser
    }
}