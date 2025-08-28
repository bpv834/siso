package com.likelion.home.chat

import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat

data class ChatUiState(
    val isCallHistoryLoading: Boolean = false,
    val isChatHistoryLoading: Boolean = false,
    val isImagedLoaded: Boolean = false,
    val callHistory: List<CallHistory> = emptyList(),
    val chatHistory: List<ChatHistory> = emptyList(),
    val myChatList: List<MyChat> = emptyList(),
    val partnerChatList: List<PartnerChat> = emptyList(),
    val myChat: MyChat? = null,
    val error: String? = null,
)