package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCallHistoryUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Flow<List<CallHistory>> =
        repository.getCallList()
}