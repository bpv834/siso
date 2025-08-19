package com.likelion.data.login.repository

import com.likelion.domain.login.model.TokenPair
import com.likelion.domain.login.repository.LoginRepository
import com.likelion.remote.api.KakaoAuthApiService
import com.likelion.remote.model.request.KakaoAuthRequest
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoAuthApiService
) : LoginRepository {
    override suspend fun exchangeKakaoToken(
        kakaoAccessToken: String,
        codeVerifier: String
    ): TokenPair {
        val dto = kakaoApi.loginWithKakao(
            KakaoAuthRequest(
                accessToken = kakaoAccessToken,
                codeVerifier = codeVerifier
            )
        )
        return TokenPair(
            accessToken = dto.accessToken,
            refreshToken = dto.refreshToken
        )
    }
}