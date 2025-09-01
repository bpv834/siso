package com.likelion.domain.notification.model

data class Call(
    val id : String,
    val callerId : String,
    val callerName: String,
    val callerImage: String,
    val agoraChannel: String,
    val agoraToken: String,
)