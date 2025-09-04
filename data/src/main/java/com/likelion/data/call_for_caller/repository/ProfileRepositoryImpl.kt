package com.likelion.data.call_for_caller.repository

import com.likelion.data.call_for_caller.mapper.toDomain
import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.domain.call_for_caller.repository.ProfileRepository
import com.likelion.remote.api.UserApiService
import com.likelion.remote.model.response.UserProfileResponse
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : ProfileRepository {
    override suspend fun getUserProfile(userId: Long): Result<UserProfileModel> {
        return runCatching {
            Timber.d("사용자 프로필을 가져옵니다. userId: $userId") //  API 호출 전 로그
            val profileResponse: UserProfileResponse = userApiService.getUserProfile(userId)
            val domainModel = profileResponse.toDomain()

            Timber.d("성공적으로 프로필을 변환했습니다: $domainModel") //  성공 시 로그
            domainModel
        }.onFailure { exception ->
            Timber.e(exception, "사용자 프로필 가져오기 실패: ${exception.message}") //  실패 시 로그
            // 예외는 onFailure 블록에서 이미 처리되므로,
            // 여기서 별도의 Result.failure를 반환할 필요는 없습니다.
        }
    }
}