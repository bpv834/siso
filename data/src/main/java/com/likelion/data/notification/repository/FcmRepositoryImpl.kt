package com.likelion.data.notification.repository

import com.likelion.data.notification.mapper.toRequest
import com.likelion.domain.notification.model.NotificationModel
import com.likelion.domain.notification.model.UserModel
import com.likelion.domain.notification.repository.FcmRepository
import com.likelion.remote.api.UserApiService
import timber.log.Timber
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : FcmRepository {
    override suspend fun getUserById(id: Long): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun updateFcmAllow(
        notificationModel: NotificationModel,
        accessToken: String,
    ): Result<Unit> {
        return try {
            val request = notificationModel.toRequest()
            val response = userApiService.updateNotification(
                accessToken = accessToken,
                request = request
            )

            if (response.isSuccessful) {
                Timber.d("FCM 알림 동의 업데이트 성공: ${response.code()} ${response.message()}")
                Result.success(Unit)
            } else {
                Timber.d("FCM 알림 동의 업데이트 실패: ${response.code()} ${response.message()}")
                Result.failure(Exception("Update failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Timber.d("FCM 알림 동의 업데이트 예외: ${e.message}")
            Result.failure(e)
        }
    }
}