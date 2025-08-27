package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    // 로컬 토큰 가져오는 함수
    suspend operator fun invoke(): Flow<BasicToken?> {
        return repository.getLocalToken()
    }
}