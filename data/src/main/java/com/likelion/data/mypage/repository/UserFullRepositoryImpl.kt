package com.likelion.data.mypage.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.likelion.data.mypage.mapper.dataToDomain
import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.enums.DrinkingCapacity
import com.likelion.domain.enums.Mbti
import com.likelion.domain.enums.PreferenceSex
import com.likelion.domain.enums.Religion
import com.likelion.domain.enums.Sex
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.repository.UserFullRepository
import com.likelion.remote.api.InterestApiService
import com.likelion.remote.api.UserApiService
import com.likelion.remote.api.VoiceApiService
import com.likelion.util.Interest
import com.likelion.util.Meeting
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UserFullRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val voiceApiService: VoiceApiService,
    private val interestApiService: InterestApiService
) : UserFullRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getUserById(
        accessToken: String
    ): UsersFullModel {

        try {
//            // 유저 야이디를 불러옴
//            val token = "Bearer $accessToken"
//            val user = userApiService.getUserId(token)
//            val id = user.body()?.data?.id ?: 1L
//            val userId = user.body()?.data?.email ?: ""
//            d("token","$id")
//            d("token","$userId")
//            val userProfileResponse = userApiService.getUserProfile(token,id)
//            val userProfile = userProfileResponse.body()!!
//            val (drinkingCapacity, religion, smoke, age, nickname,introduce,
//                location, sex, preferenceSex, profileImages, meetings) = userProfile

            val userEntity = UsersFullEntity(
                id = 1L,
                userId = "fdf",
                age = 65,
                nickname = "코딩러",
                voiceUrl = "https://samplelib.com/lib/preview/mp3/sample-12s.mp3",
                introduce = "65세 코딩러 입니다 \n 65세 코딩러 입니다 \n65세 코딩러 입니다 \n65세 코딩러 입니다 \n65세 코딩러 입니다 \n",
                profileImage = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                location = "서울특별시 종로구",
                sex = Sex.MALE,
                preferenceSex = PreferenceSex.MALE,
                isSmoke = true,
                drinkingCapacity = DrinkingCapacity.OCCASIONALLY,
                religion = Religion.CHRISTIANITY,
                mbti = Mbti.ENFJ,
                interest = listOf(
                    Interest.INTERIOR,
                    Interest.GOOD_RESTAURANT
                ),
                meeting = listOf(
                    Meeting.CLUB_ACTIVITY,
                    Meeting.BOOK_CLUB
                )
            )
            return userEntity.dataToDomain()
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
            throw Exception("예기치 못한 오류: ${e}")
        }



    }
}