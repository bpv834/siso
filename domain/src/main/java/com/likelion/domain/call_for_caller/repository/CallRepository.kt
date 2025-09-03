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
    suspend fun denyCall(
        accessToken: String,
        request: CallModel
    ): Result<CallRejectResponseModel>
}