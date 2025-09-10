package com.likelion.domain.notification.usecase

import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.model.NotificationModel
import com.likelion.domain.notification.repository.FcmRepository
import com.likelion.domain.notification.repository.FcmTokenRepository


class UpdateUserAllowUseCase(
    private val repository: FcmRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        notificationModel: NotificationModel
    ): Result<Unit> {
        return repository.updateFcmAllow(
            notificationModel = notificationModel,
            accessToken = accessToken
        )
    }
}