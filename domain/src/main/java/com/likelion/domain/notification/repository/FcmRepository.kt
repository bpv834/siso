package com.likelion.domain.notification.repository

import com.likelion.domain.notification.model.NotificationModel
import com.likelion.domain.notification.model.UserModel
import javax.inject.Inject

interface FcmRepository {
    suspend fun getUserById(id: Long): UserModel

    suspend fun updateFcmToken(notificationRequest : NotificationModel)
}

