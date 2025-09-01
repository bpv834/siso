package com.likelion.siso.navigation

import com.likelion.domain.notification.model.Call

sealed class UiEvent {
    data class IncomingCall(val call: Call) : UiEvent()
    object CallReject : UiEvent()              // 상대방이 거절
    object CallRejectedByMe : UiEvent()       // 내가 거절
    data class Error(val message: String) : UiEvent()
}