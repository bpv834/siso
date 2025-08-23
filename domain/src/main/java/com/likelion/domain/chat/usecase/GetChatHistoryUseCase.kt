package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatHistoryUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Flow<List<ChatHistory>> =
        repository.getMsgList()
}