package com.example.notification

sealed class FcmEvent {
    data class Call(
        val id : String,
        val callerName: String,
        val callerImage: String,
        val agoraChannel: String,
        val agoraToken: String,
        val callerId : String,
    ) : FcmEvent()

    data class Message(
        val senderId: Long,
        val senderName: String,
        val senderImage: String,
        val messageText: String
    ) : FcmEvent()

    object Reject : FcmEvent()
}
