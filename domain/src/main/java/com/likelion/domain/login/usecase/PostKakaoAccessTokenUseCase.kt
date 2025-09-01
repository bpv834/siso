package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.PostKakaoResult
import com.likelion.domain.login.repository.LoginRepository
import javax.inject.Inject

class PostKakaoAccessTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(token: String): PostKakaoResult {
        return repository.postKakaoAccessToken(token)
    }
}