package com.likelion.siso.navigation

import com.example.notification.FcmEvent
import com.likelion.domain.notification.model.Call

fun FcmEvent.Call.toCall(): Call {
    return Call(
        id = id,
        callerId = callerId,
        callerName = callerName,
        callerImage = callerImage,
        agoraChannel = agoraChannel,
        agoraToken = agoraToken
    )
}