package com.likelion.domain.auth.model

// 카카오 토큰 발급 상태
sealed interface KakaoTokenResult {
    data class Success(val token: String) : KakaoTokenResult
    data object Canceled : KakaoTokenResult
    data class Error(val cause: Throwable) : KakaoTokenResult
}