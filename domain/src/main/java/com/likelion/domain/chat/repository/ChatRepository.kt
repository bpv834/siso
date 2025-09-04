package com.likelion.domain.chat.repository

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.model.ChatRoom
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat
import com.likelion.domain.model.ChatRoomsModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getCallList(): Flow<List<CallHistory>>
    suspend fun getMsgList(): Flow<List<ChatHistory>>
    suspend fun sendChat(msg: String): MyChat
    suspend fun getChat(): Flow<List<PartnerChat>>
    suspend fun removeChatRoom(id: Long)
    suspend fun removeCallHistory(id: Long)
    suspend fun limitSendChat(chatRoomId: Long, msg: String): MyChat

    // 하위부터 api연결
    suspend fun getChatRoomList(accessToken: String): Flow<List<ChatRoom>>
}