package com.likelion.data.chat.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.likelion.data.chat.mapper.toDomain
import com.likelion.data.chat.model.CallHistoryEntity
import com.likelion.data.chat.model.ChatHistoryEntity
import com.likelion.data.chat.model.MyChatEntity
import com.likelion.data.chat.model.PartnerChatEntity
import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat
import com.likelion.domain.chat.repository.ChatRepository
import com.likelion.remote.api.ChatApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val data: ChatApiService
) : ChatRepository {
    private val dummyCallHistory = MutableStateFlow(createDummyCallHistory())
    private val dummyChatHistory = MutableStateFlow(createDummyChatHistory())
    private val dummyPartnerChat = MutableStateFlow(createDummyPartnerChat())

    private val limitCounters = MutableStateFlow<Map<Long, Int>>(emptyMap())


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCallList(): Flow<List<CallHistory>> {
        return dummyCallHistory
            .map { list ->
                list.map { it.toDomain() }
            }
            .onStart { delay(3000) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMsgList(): Flow<List<ChatHistory>> {
        return dummyChatHistory
            .map { list ->
                list.map { it.toDomain() }
            }
            .onStart { delay(3000) }
    }

    // 메시지 보내기
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendChat(msg: String): MyChat {
        val now = System.currentTimeMillis()
        val entity = MyChatEntity(
            chatRoomId = 0,
            msg = msg,
            time = now,
            showTime = true
        )
        return entity.toDomain()
    }

    // 메시지 가져오기
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getChat(): Flow<List<PartnerChat>> {
        return dummyPartnerChat.map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun removeChatRoom(id: Long) {
        dummyChatHistory.update { list ->
            list.filterNot { it.chatRoomId == id }
        }
    }

    override suspend fun removeCallHistory(id: Long) {
        dummyCallHistory.update { list ->
            list.filterNot { it.callId == id }
        }
    }

    // 커스텀 예외
    class LimitReachedException(val roomId: Long, val count: Int) :
        IllegalStateException("chatRoomId=$roomId, count=$count (limit=5)")


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun limitSendChat(chatRoomId: Long, msg: String): MyChat {
        val curr = limitCounters.value[chatRoomId] ?: 0
        if (curr >= 5) {
            throw LimitReachedException(chatRoomId, curr)
        }
        limitCounters.update { old ->
            val next = (old[chatRoomId] ?: 0) + 1
            old.toMutableMap().apply { put(chatRoomId, next) }
        }
        // 실제 메시지 생성(더미)
        val now = System.currentTimeMillis()
        val entity = MyChatEntity(
            chatRoomId = chatRoomId,
            msg = msg,
            time = now,
            showTime = true
        )
        return entity.toDomain()
    }

    override suspend fun getChatRoomList() {
        // 채팅방가져오기
    }

    private fun createDummyPartnerChat(): List<PartnerChatEntity> {
        return listOf(
            PartnerChatEntity(
                partnerImg = "https://picsum.photos/200/200",
                partnerMsg = "안녕하세요",
                partnerTime = System.currentTimeMillis(),
                showTime = true
            ),
            PartnerChatEntity(
                partnerImg = "https://picsum.photos/200/200",
                partnerMsg = "Hello",
                partnerTime = System.currentTimeMillis(),
                showTime = true
            ),
            PartnerChatEntity(
                partnerImg = "https://picsum.photos/200/200",
                partnerMsg = "こんにちは",
                partnerTime = System.currentTimeMillis(),
                showTime = true
            )
        )
    }

    private fun createDummyChatHistory(): List<ChatHistoryEntity> {
        return listOf(
            ChatHistoryEntity(
                chatRoomId = 1,
                profileImage = "https://picsum.photos/200/200",
                nickName = "코틀린",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは",
                isView = true,
            ),
            ChatHistoryEntity(
                chatRoomId = 2,
                profileImage = "https://picsum.photos/200/201",
                nickName = "자바",
                callTime = System.currentTimeMillis(),
                currentMsg = "채팅방이 개설되었습니다",
                isView = false,
            ),
//            ChatHistoryEntity(
//                chatRoomId = 3,
//                profileImage = "https://picsum.photos/200/202",
//                nickName = "씨",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            ),
//            ChatHistoryEntity(
//                chatRoomId = 4,
//                profileImage = "https://picsum.photos/200/203",
//                nickName = "씨플플",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            ),
//            ChatHistoryEntity(
//                chatRoomId = 5,
//                profileImage = "https://picsum.photos/200/204",
//                nickName = "스위프트",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            ),
//            ChatHistoryEntity(
//                chatRoomId = 6,
//                profileImage = "https://picsum.photos/200/205",
//                nickName = "삼성",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            ),
//            ChatHistoryEntity(
//                chatRoomId = 7,
//                profileImage = "https://picsum.photos/200/206",
//                nickName = "애플",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            ),
//            ChatHistoryEntity(
//                chatRoomId = 8,
//                profileImage = "https://picsum.photos/200/207",
//                nickName = "닉네임은여덟글자",
//                callTime = System.currentTimeMillis(),
//                currentMsg = "채팅방이 개설되었습니다"
//            )
        )
    }

    private fun createDummyCallHistory(): List<CallHistoryEntity> {
        return listOf(
            CallHistoryEntity(
                callId = 0,
                profileImage = "https://picsum.photos/200/200",
                nickName = "코틀린",
                callTime = System.currentTimeMillis()
            ),
            CallHistoryEntity(
                callId = 1,
                profileImage = "https://picsum.photos/200/201",
                nickName = "자바",
                callTime = System.currentTimeMillis() - 3600_000
            ),
            CallHistoryEntity(
                callId = 2,
                profileImage = "https://picsum.photos/200/202",
                nickName = "씨",
                callTime = System.currentTimeMillis() - 86_400_000
            ),
//            CallHistoryEntity(
//                callId = 3,
//                profileImage = "https://picsum.photos/200/203",
//                nickName = "씨플플",
//                callTime = System.currentTimeMillis() - 2 * 86_400_000
//            ),
//            CallHistoryEntity(
//                callId = 4,
//                profileImage = "https://picsum.photos/200/204",
//                nickName = "스위프트",
//                callTime = System.currentTimeMillis() - 3 * 86_400_000
//            ),
//            CallHistoryEntity(
//                callId = 5,
//                profileImage = "https://picsum.photos/200/205",
//                nickName = "삼성",
//                callTime = System.currentTimeMillis() - 4 * 86_400_000
//            ),
//            CallHistoryEntity(
//                callId = 6,
//                profileImage = "https://picsum.photos/200/206",
//                nickName = "애플",
//                callTime = System.currentTimeMillis() - 5 * 86_400_000
//            )
        )
    }
}