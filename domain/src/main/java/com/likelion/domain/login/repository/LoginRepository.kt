package com.likelion.domain.login.repository

import com.likelion.domain.login.model.TokenPair

interface LoginRepository {
    suspend fun exchangeKakaoToken(kakaoAccessToken: String, codeVerifier: String): TokenPair
}