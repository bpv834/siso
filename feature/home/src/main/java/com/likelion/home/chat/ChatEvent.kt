package com.likelion.home.chat

sealed class ChatEvent {
    data object LoadCallHistory : ChatEvent()
    data object LoadChatHistory : ChatEvent()
    data class RemoveCallHistory(val id: Long) : ChatEvent()
    data class RemoveChatHistory(val id: Long) : ChatEvent()
}