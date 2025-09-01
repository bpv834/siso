package com.likelion.domain.notification.repository

import com.likelion.domain.notification.model.FcmToken

interface FcmTokenRepository {
    suspend fun sendToken(token: FcmToken): Result<Unit>

}