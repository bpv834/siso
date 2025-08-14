package com.likelion.kakao_auth.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.provider.KakaoTokenProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import javax.inject.Inject

class KakaoTokenProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userApiClient: UserApiClient
) : KakaoTokenProvider {
    override suspend fun fetchKakaoToken(): KakaoTokenResult =
        suspendCancellableCoroutine { cont ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                when {
                    token != null -> cont.resume(KakaoTokenResult.Success(token.accessToken))
                    error is ClientError && error.reason == ClientErrorCause.Cancelled ->
                        cont.resume(KakaoTokenResult.Canceled)

                    error != null -> cont.resume(KakaoTokenResult.Error(error))
                    else -> cont.resume(KakaoTokenResult.Error(IllegalStateException("Unknown login state")))
                }
            }
            if (userApiClient.isKakaoTalkLoginAvailable(context)) {
                userApiClient.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        // 사용자가 권한 요청 화면 등에서 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            cont.resume(KakaoTokenResult.Canceled)
                            return@loginWithKakaoTalk
                        }
                        // 톡 계정 미연결 등 → 계정 로그인 폴백
                        userApiClient.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        cont.resume(KakaoTokenResult.Success(token.accessToken))
                    } else {
                        cont.resume(KakaoTokenResult.Error(IllegalStateException("No token and no error")))
                    }
                }
            } else {
                userApiClient.loginWithKakaoAccount(context, callback = callback)
            }
        }

}