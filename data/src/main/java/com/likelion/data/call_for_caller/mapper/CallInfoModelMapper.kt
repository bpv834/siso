package com.likelion.data.call_for_caller.mapper

import com.likelion.domain.call_for_caller.model.CallInfoModel
import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.remote.model.request.CallInfoRequest
import com.likelion.remote.model.response.CallInfoDto

fun CallInfoRequest.toDomain(): CallModel {
    return CallModel(
        id = this.id,
        callerId = this.callerId,
        channelName = this.channelName,
        agoraToken = this.token, // 'token'을 'agoraToken'으로 매핑
        receiverId = this.receiverId
    )
}