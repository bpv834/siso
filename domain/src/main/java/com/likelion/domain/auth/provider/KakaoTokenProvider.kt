package com.likelion.domain.auth.provider

import com.likelion.domain.auth.model.KakaoTokenResult

interface KakaoTokenProvider {
    suspend fun fetchKakaoToken(): KakaoTokenResult
}