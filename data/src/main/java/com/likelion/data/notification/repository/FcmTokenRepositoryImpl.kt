package com.likelion.data.notification.repository

import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.repository.FcmTokenRepository
import com.likelion.remote.api.FcmApiService
import com.likelion.remote.model.request.FcmTokenRequest
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject


class FcmTokenRepositoryImpl @Inject constructor(
    // private val api: FcmApiService,
    private val getTokenUse: GetTokenAllUseCase, // 앱에서 JWT 가져오는 usecase
    // fcm 토큰 서버로 보내는 usecase
    val fcmApiService: FcmApiService,
) : FcmTokenRepository {
    // 클라이언트 sdk 토큰을 따서 서버에 토큰을 매핑하는 메서드
    override suspend fun sendToken(token: FcmToken) {
        // flow라서 first() 로 받음
        val user = getTokenUse.invoke().first()
        val accessToken = user?.accessToken

        Timber.d("user = $user")
        Timber.d("accessToken= $accessToken")
        val request = FcmTokenRequest(
            userId = user?.userInfo!!.id,
            token = token.token
        )
        fcmApiService.sendToken(jwt = "Bearer $accessToken", body = request)
    }

}