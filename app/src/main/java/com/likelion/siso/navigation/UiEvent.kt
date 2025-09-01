package com.likelion.siso.navigation

import com.likelion.domain.notification.model.Call
import com.likelion.domain.notification.model.UserModel

sealed class UiEvent {
    data class IncomingCall(val call: Call) : UiEvent()
    // data class IncomingMessage(val message: Message) : UiEvent()
    object CallDismissed : UiEvent()
    object MessageDismissed : UiEvent()

    // 닫기 이벤트를 분리한 이유는 전화는 닫으면 서버에 거절 통신을 해야하기 때문
}