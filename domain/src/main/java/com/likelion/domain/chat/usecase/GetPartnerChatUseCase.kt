package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.PartnerChat
import com.likelion.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPartnerChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Flow<List<PartnerChat>> {
        return repository.getChat()
    }
}