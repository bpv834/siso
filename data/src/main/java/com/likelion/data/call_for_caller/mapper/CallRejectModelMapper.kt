package com.likelion.data.call_for_caller.mapper

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.model.CallRejectResponseModel
import com.likelion.remote.model.request.RejectCallRequest
import com.likelion.remote.model.response.RejectCallResponse

fun CallModel.toRemote(): RejectCallRequest = RejectCallRequest(
    id = this.id.toLong(),              // 도메인에서는 Int였으므로 Long으로 변환
    channelName = this.channelName,
    token = this.agoraToken ?: "",           // null일 수 있으므로 빈 문자열 처리
    callerId = this.callerId,
    receiverId = this.receiverId,
)

// Remote DTO → Domain 모델 매퍼
fun RejectCallResponse.toDomain(): CallRejectResponseModel = CallRejectResponseModel(
    success = this.success,
    message = this.message
)