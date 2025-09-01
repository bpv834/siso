package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.repository.ChatRepository
import javax.inject.Inject

class LimitSendChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatRoomId: Long, chat: String): MyChat {
        return repository.limitSendChat(chatRoomId, chat)
    }
}