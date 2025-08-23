package com.likelion.domain.chat.repository

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getCallList(): Flow<List<CallHistory>>
    suspend fun getMsgList(): Flow<List<ChatHistory>>
}