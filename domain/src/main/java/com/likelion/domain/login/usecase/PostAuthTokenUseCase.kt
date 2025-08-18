package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.TokenPair
import com.likelion.domain.login.repository.LoginRepository
import javax.inject.Inject

class PostAuthTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(
        kakaoAccessToken: String,
        codeVerifier: String
    ): TokenPair {
        return repository.exchangeKakaoToken(
            kakaoAccessToken = kakaoAccessToken,
            codeVerifier = codeVerifier
        )
    }
}