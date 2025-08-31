package com.likelion.domain.notification.model

data class Call(
    val callerName: String,
    val callerImage: String,
    val agoraChannel: String,
    val agoraToken: String
)