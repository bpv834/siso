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
    override suspend fun sendToken(token: FcmToken): Result<Unit> {
        return runCatching {
            // 이 코드 블록에서 발생하는 모든 예외(네트워크 오류, null 포인터 등)는
            // 'runCatching'에 의해 잡혀서 'Result.failure(e)'로 반환됩니다.
            val user = getTokenUse.invoke().first()
            val accessToken = user?.accessToken

            val request = FcmTokenRequest(
                userId = user?.userInfo!!.id,
                token = token.token
            )

            fcmApiService.sendToken(jwt = "Bearer $accessToken", body = request)
        }
    }

}