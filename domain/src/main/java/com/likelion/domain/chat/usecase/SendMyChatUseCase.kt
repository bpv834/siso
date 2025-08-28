package com.likelion.domain.chat.usecase

import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.repository.ChatRepository
import javax.inject.Inject

class SendMyChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(msg: String): MyChat {
        return repository.sendChat(msg)
    }
}