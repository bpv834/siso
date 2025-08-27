package com.likelion.domain.login.model

// PostKakao 결과값
sealed interface PostKakaoResult {
    data class Success(val token: BasicToken) : PostKakaoResult
    data class Error(val code: Int, val message: String?) : PostKakaoResult
    data class Exception(val throwable: Throwable) : PostKakaoResult
}