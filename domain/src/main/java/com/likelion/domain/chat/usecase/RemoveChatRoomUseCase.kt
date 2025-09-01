package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.repository.ChatRepository
import javax.inject.Inject

class RemoveChatRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.removeChatRoom(id)
    }
}