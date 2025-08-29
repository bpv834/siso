package com.likelion.domain.notification.usecase

import com.likelion.domain.login.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFcmTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    // 로컬 토큰 가져오는 함수
    suspend operator fun invoke(): Flow<String?> {
        return repository.getFcmToken()
    }
}