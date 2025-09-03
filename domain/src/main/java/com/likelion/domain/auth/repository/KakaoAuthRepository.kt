package com.likelion.domain.auth.repository

import com.likelion.domain.auth.model.KakaoTokenResult

// 카카오 토큰 가져오기
interface KakaoAuthRepository {
    suspend fun fetchKakaoAccessToken(): KakaoTokenResult
}

// 서버 교환 전용
interface KakaoAuthTestRepository {
    suspend fun loginWithKakaoToken(): KakaoTokenResult
}