package com.likelion.domain.auth.usecase

import com.likelion.domain.auth.repository.KakaoAuthRepository
import javax.inject.Inject

class KakaoAuthUseCase @Inject constructor(
    private val repository: KakaoAuthRepository
) {
    operator fun invoke() {
        return repository.kakaoLogIn()
    }
}