package com.likelion.domain.auth.usecase

import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.repository.KakaoAuthRepository
import javax.inject.Inject

// 카카오 토큰 가져오는 유스케이스
class FetchKakaoTokenUseCase @Inject constructor(
    private val repository: KakaoAuthRepository
) {
    suspend operator fun invoke(): KakaoTokenResult {
        return repository.fetchKakaoAccessToken()
    }
}