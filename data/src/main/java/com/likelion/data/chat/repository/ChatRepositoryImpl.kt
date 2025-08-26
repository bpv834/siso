package com.likelion.data.chat.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.likelion.data.chat.mapper.toDomain
import com.likelion.data.chat.model.CallHistoryEntity
import com.likelion.data.chat.model.ChatHistoryEntity
import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
) : ChatRepository {
    private val dummyCallHistory = MutableStateFlow(createDummyCallHistory())
    private val dummyChatHistory = MutableStateFlow(createDummyChatHistory())

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCallList(): Flow<List<CallHistory>> {
        return dummyCallHistory.map { list ->
            delay(3000)
            list.map { it.toDomain() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMsgList(): Flow<List<ChatHistory>> {
        return dummyChatHistory.map { list ->
            delay(3000)
            list.map { it.toDomain() }
        }
    }

    private fun createDummyChatHistory(): List<ChatHistoryEntity> {
        return listOf(
            ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/200",
                nickName = "코틀린",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは",
                isView = true,
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/201",
                nickName = "자바",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは",
                isView = true,
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/202",
                nickName = "씨",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/203",
                nickName = "씨플플",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/204",
                nickName = "스위프트",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/205",
                nickName = "삼성",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/206",
                nickName = "애플",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            ), ChatHistoryEntity(
                profileImage = "https://picsum.photos/200/206",
                nickName = "닉네임은여덟글자",
                callTime = System.currentTimeMillis(),
                currentMsg = "Hello 안녕하세요 こんにちは"
            )

        )
    }

    private fun createDummyCallHistory(): List<CallHistoryEntity> {
        return listOf(
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/200",
                nickName = "코틀린",
                callTime = System.currentTimeMillis()
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/201",
                nickName = "자바",
                callTime = System.currentTimeMillis() - 3600_000
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/202",
                nickName = "씨",
                callTime = System.currentTimeMillis() - 86_400_000
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/203",
                nickName = "씨플플",
                callTime = System.currentTimeMillis() - 2 * 86_400_000
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/204",
                nickName = "스위프트",
                callTime = System.currentTimeMillis() - 3 * 86_400_000
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/205",
                nickName = "삼성",
                callTime = System.currentTimeMillis() - 4 * 86_400_000
            ),
            CallHistoryEntity(
                profileImage = "https://picsum.photos/200/206",
                nickName = "애플",
                callTime = System.currentTimeMillis() - 5 * 86_400_000
            )
        )
    }
}