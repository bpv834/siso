package com.likelion.data.call.mapper

import com.likelion.domain.call.model.CallModel
import com.likelion.remote.model.request.CallInfoRequest

fun CallInfoRequest.toDomain(): CallModel {
    return CallModel(
        id = this.id,
        callerId = this.callerId,
        channelName = this.channelName,
        agoraToken = this.token, // 'token'을 'agoraToken'으로 매핑
        receiverId = this.receiverId
    )
}