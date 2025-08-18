package com.likelion.kakao_auth.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.repository.KakaoAuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

// 카카오 토큰 가져오는 구현체
class KakaoAuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoAuthRepository {
    override suspend fun fetchKakaoAccessToken(): KakaoTokenResult =
        suspendCancellableCoroutine { cont ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                when {
                    error != null -> cont.resume(KakaoTokenResult.Error(error))
                    token != null -> cont.resume(KakaoTokenResult.Success(token.accessToken))
                    else -> cont.resume(KakaoTokenResult.Canceled)
                }
            }


            val userApi = UserApiClient.instance
            if (userApi.isKakaoTalkLoginAvailable(context)) {
                userApi.loginWithKakaoTalk(context , callback = callback)
            } else {
                userApi.loginWithKakaoAccount(context, callback = callback)
            }

        }

}