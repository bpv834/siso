package com.likelion.home.chat

sealed class ChatEvent {
    data object LoadCallHistory : ChatEvent()
    data object LoadChatHistory : ChatEvent()
//    data class ChatHistoryImageLoaded(val id:Long) : ChatEvent()
//    data class CallHistoryImageLoaded(val id:Long) : ChatEvent()
    data class RemoveCallHistory(val id: Long) : ChatEvent()
    data class RemoveChatHistory(val id: Long) : ChatEvent()
    data class SendChat(val chat: String) : ChatEvent()
}