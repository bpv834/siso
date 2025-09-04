package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.ChatRoom
import com.likelion.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(accessToken: String): Flow<List<ChatRoom>> {
        return repository.getChatRoomList(accessToken = accessToken)
    }
}