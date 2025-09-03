package com.likelion.domain.call_for_caller.repository

import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.model.CallInfoModel
import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.model.CallRejectResponseModel
import com.likelion.domain.call_for_caller.model.CallResponseModel
import kotlinx.coroutines.flow.SharedFlow

interface CallRepository {
    val agoraEvents: SharedFlow<AgoraEvent> // 이벤트 Flow 추가
    suspend fun startCall(receiverId: Long, accessToken: String): Result<CallModel>
    suspend fun evaluationAfterEndCall(callModel: CallModel, isKeepGoing: Boolean, accessToken: String): Result<CallResponseModel>
    suspend fun rejectCall(): Result<Unit>
    // 수신자가 전화를 받지 않는다고 서버에 전달하는것 발신자가 알림을 받기위함
    suspend fun denyCall(
        accessToken: String,
        request: CallModel
    ): Result<CallRejectResponseModel>
}