package com.likelion.siso.navigation

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.notification.model.Call

sealed class UiEvent {
    data class IncomingCall(val call: Call) : UiEvent()
    object CallReject : UiEvent() // 상대방이 거절
    object CallRejectedByMe: UiEvent() // 내가 거절
    data class Error(val message: String) : UiEvent()
    data class NavigateToCallScreen (val call : Call): UiEvent() // 수신자가 전화 받았을 때 이동 이벤트
}