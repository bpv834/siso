package com.likelion.domain.auth.repository

import com.likelion.domain.auth.model.KakaoTokenResult

// 카카오 토큰 가져오기
interface KakaoAuthRepository {
    suspend fun fetchKakaoAccessToken(): KakaoTokenResult
}