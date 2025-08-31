package com.likelion.domain.chat.repository

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getCallList(): Flow<List<CallHistory>>
    suspend fun getMsgList(): Flow<List<ChatHistory>>
    suspend fun sendChat(msg: String): MyChat
    suspend fun getChat(): Flow<List<PartnerChat>>
}