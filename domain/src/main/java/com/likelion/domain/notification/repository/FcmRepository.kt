package com.likelion.domain.notification.repository

import com.likelion.domain.notification.model.NotificationModel
import com.likelion.domain.notification.model.UserModel

interface FcmRepository {
    suspend fun getUserById(id: Long): UserModel

    suspend fun updateFcmAllow(notificationModel : NotificationModel, accessToken : String) : Result<Unit>
}

