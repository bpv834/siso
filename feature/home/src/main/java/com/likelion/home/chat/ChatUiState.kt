package com.likelion.home.chat

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory

data class ChatUiState(
    val isCallHistoryLoading: Boolean = false,
    val isChatHistoryLoading: Boolean = false,
    val error: String? = null,
    val callHistory: List<CallHistory> = emptyList(),
    val chatHistory: List<ChatHistory> = emptyList()
)