package com.likelion.data.mypage.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log.d
import androidx.annotation.RequiresExtension
import com.google.gson.annotations.SerializedName
import com.likelion.data.mypage.mapper.dataToDomain
import com.likelion.util.Meeting
import com.likelion.util.Interest
import com.likelion.data.mypage.model.UsersFullEntity
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.repository.UserFullRepository
import com.likelion.remote.api.InterestApiService
import com.likelion.remote.api.UserApiService
import com.likelion.remote.api.VoiceApiService
import com.likelion.remote.model.response.ImageResponse
import com.likelion.util.DrinkingCapacity
import com.likelion.util.Mbti
import com.likelion.util.PreferenceSex
import com.likelion.util.Religion
import com.likelion.util.Sex
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            // 유저 야이디를 불러옴
            val token = "Bearer $accessToken"
            val user = userApiService.getUserId(token)
            val id = user.body()?.data?.id ?: 1L
            val userId = user.body()?.data?.email ?: ""

            val userProfileResponse = userApiService.getUserProfile(token,id)
            val userProfile = userProfileResponse.body()!!
            val (drinkingCapacity, religion, smoke, age, nickname,introduce,
                location, sex, preferenceSex, profileImages, meetings) = userProfile

//            val userEntity = UsersFullEntity(
//                id = id,
//                userId = userId,
//                age = 1,
//                nickname = "",
//                voiceUrl = TODO(),
//                introduce = TODO(),
//                profileImage = TODO(),
//                location = TODO(),
//                sex = TODO(),
//                preferenceSex = TODO(),
//                isSmoke = TODO(),
//                drinkingCapacity = TODO(),
//                religion = TODO(),
//                mbti = TODO(),
//                interest = TODO(),
//                meeting = TODO()
//            )
            return UsersFullModel(
                id = 1,
                age = 1,
                nickname = "",
                voiceUrl = "",
                introduce = "",
                location = "",
                sex = "",
                preferenceSex = "",
                isSmoke = "",
                drinkingCapacity = "",
                religion = "",
                mbti = "",
                userImages = "",
                interests = listOf(),
                meeting = listOf(),
            )
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

    // 1. 토큰 자동 삽입 Interceptor
    class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request()
            val token = tokenProvider()

            val requestBuilder = originalRequest.newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val requestWithToken = requestBuilder.build()
            val response = chain.proceed(requestWithToken)

            // 401 디버깅
            if (response.code == 401) {
                println("⚠️ 401 Unauthorized 발생! 요청 헤더 확인 필요")
                println("Request Headers: ${requestWithToken.headers}")
            }

            return response
        }
    }
}