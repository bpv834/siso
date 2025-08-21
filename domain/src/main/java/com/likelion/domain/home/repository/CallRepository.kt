package com.likelion.domain.home.repository

import com.likelion.domain.home.model.AgoraEvent
import com.likelion.domain.home.model.CallInfoModel
import kotlinx.coroutines.flow.SharedFlow

interface CallRepository {
    suspend fun startCall(callerId : Long,receiverId:Long):  Result<CallInfoModel>
    suspend fun endCall()
    val agoraEvents: SharedFlow<AgoraEvent> // 이벤트 Flow 추가

}