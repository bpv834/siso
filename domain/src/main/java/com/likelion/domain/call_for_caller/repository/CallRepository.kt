package com.likelion.domain.call_for_caller.repository

import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.model.CallInfoModel
import kotlinx.coroutines.flow.SharedFlow

interface CallRepository {
    suspend fun startCall(callerId : Long,receiverId:Long):  Result<CallInfoModel>
    suspend fun endCall()
    val agoraEvents: SharedFlow<AgoraEvent> // 이벤트 Flow 추가

}