package com.likelion.data.call.mapper

import com.likelion.domain.call.model.CallModel
import com.likelion.remote.model.request.CallInfoRequest

fun CallModel.toRemote(): CallInfoRequest = CallInfoRequest(
    id = this.id,              // 도메인에서는 Int였으므로 Long으로 변환
    channelName = this.channelName,
    token = this.agoraToken ?: "",           // null일 수 있으므로 빈 문자열 처리
    callerId = this.callerId,
    receiverId = this.receiverId,
)

