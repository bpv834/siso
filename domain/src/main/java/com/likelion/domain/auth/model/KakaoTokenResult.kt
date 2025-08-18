package com.likelion.domain.auth.model

// 카카오 SDK -> 성공/실패/취소
sealed interface KakaoTokenResult {
    data class Success(val token: String) : KakaoTokenResult
    object Canceled : KakaoTokenResult
    data class Error(val cause: Throwable) : KakaoTokenResult
}