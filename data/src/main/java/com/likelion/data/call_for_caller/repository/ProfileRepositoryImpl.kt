package com.likelion.data.call_for_caller.repository

import com.likelion.data.call_for_caller.mapper.toDomain
import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.domain.call_for_caller.repository.ProfileRepository
import com.likelion.remote.api.UserApiService
import com.likelion.remote.model.response.UserProfileResponse

import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : ProfileRepository {
    override suspend fun getUserProfile(
        accessToken: String,
        userId: Long
    ): Result<UserProfileModel> {
        return try {
            Timber.d("사용자 프로필을 가져옵니다. userId: $userId, accessToken: $accessToken")

            // Bearer 토큰 형식으로 API 호출
            val response: Response<UserProfileResponse> =
                userApiService.getUserProfile(accessToken ="Bearer $accessToken", userId = userId)

            if (response.isSuccessful) {
                val profileResponse = response.body()
                if (profileResponse != null) {
                    val domainModel = profileResponse.toDomain()
                    Timber.d("성공적으로 프로필을 변환했습니다: $domainModel")
                    // HTTP 성공 & 응답 본문 존재 시, Result.success() 반환
                    Result.success(domainModel)
                } else {
                    val errorMessage = "응답 본문이 null입니다. HTTP ${response.code()}"
                    Timber.e(errorMessage)
                    // 응답 본문이 null일 경우, Result.failure() 반환
                    Result.failure(Exception(errorMessage))
                }
            } else {
                // API 호출은 성공했으나, HTTP 에러 코드를 받은 경우
                val errorMessage = "API 호출 실패: HTTP ${response.code()}"
                Timber.e(errorMessage)
                // HTTP 에러 시, Result.failure() 반환
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            // 네트워크 오류, JSON 파싱 오류 등 예외 발생 시
            Timber.e(e, "사용자 프로필 가져오기 실패: ${e.message}")
            // 예외 발생 시, Result.failure() 반환
            Result.failure(e)
        }
    }

    override suspend fun getMyProfile(accessToken: String): Result<UserProfileModel> {
        return try {
            Timber.d("내 프로필을 가져옵니다.accessToken: $accessToken")

            // Bearer 토큰 형식으로 API 호출
            val response: Response<UserProfileResponse> =
                userApiService.getMyProfile(accessToken ="Bearer $accessToken")

            if (response.isSuccessful) {
                val profileResponse = response.body()
                if (profileResponse != null) {
                    val domainModel = profileResponse.toDomain()
                    Timber.d("성공적으로 프로필을 변환했습니다: $domainModel")
                    // HTTP 성공 & 응답 본문 존재 시, Result.success() 반환
                    Result.success(domainModel)
                } else {
                    val errorMessage = "응답 본문이 null입니다. HTTP ${response.code()}"
                    Timber.e(errorMessage)
                    // 응답 본문이 null일 경우, Result.failure() 반환
                    Result.failure(Exception(errorMessage))
                }
            } else {
                // API 호출은 성공했으나, HTTP 에러 코드를 받은 경우
                val errorMessage = "API 호출 실패: HTTP ${response.code()}"
                Timber.e(errorMessage)
                // HTTP 에러 시, Result.failure() 반환
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            // 네트워크 오류, JSON 파싱 오류 등 예외 발생 시
            Timber.e(e, "사용자 프로필 가져오기 실패: ${e.message}")
            // 예외 발생 시, Result.failure() 반환
            Result.failure(e)
        }
    }
}