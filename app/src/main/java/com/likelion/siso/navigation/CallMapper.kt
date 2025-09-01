package com.likelion.siso.navigation

import com.example.notification.FcmEvent
import com.likelion.domain.notification.model.Call

fun FcmEvent.Call.toCall(): Call {
    return Call(
        callerName = callerName,
        callerImage = callerImage,
        agoraChannel = agoraChannel,
        agoraToken = agoraToken
    )
}