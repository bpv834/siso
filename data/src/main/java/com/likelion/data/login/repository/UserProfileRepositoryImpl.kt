package com.likelion.data.login.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.likelion.data.login.mapper.toUserProfileRequest
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserProfileRepository
import com.likelion.remote.api.UserApiService
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
   // private val voiceApi: UserSignUpApi,
    ) : UserProfileRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun addProfile(
        accessToken: String,
        profile: UserSignUpProfile
    ) {
        try {
            // 1. 도메인 모델 → DTO 변환
            val userProfileRequest = profile.toUserProfileRequest()
            Timber.d("userRequest : $userProfileRequest")

            // 2. API 호출
            val response = userApiService.registerUserProfile(
                refreshToken = "Bearer $accessToken",
                request = userProfileRequest
            )

            // 3. 실패 응답 처리
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "알 수 없는 오류"
                throw Exception("프로필 등록 실패: $errorBody")
            }

            // 4. 성공 응답 처리
            val registeredUserProfile = response.body()
            Timber.d("등록 완료된 UserProfile : $registeredUserProfile")

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
    }


}