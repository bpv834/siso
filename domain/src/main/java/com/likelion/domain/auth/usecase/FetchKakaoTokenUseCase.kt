package com.likelion.domain.auth.usecase

import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.provider.KakaoTokenProvider
import javax.inject.Inject

class FetchKakaoTokenUseCase @Inject constructor(
    private val provider: KakaoTokenProvider
) {
    suspend operator fun invoke(): String? {
        return when (val result = provider.fetchKakaoToken()) {
            is KakaoTokenResult.Success -> result.token
            is KakaoTokenResult.Canceled -> null
            is KakaoTokenResult.Error -> {
                throw result.cause
            }
        }
    }
}