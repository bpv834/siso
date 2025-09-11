package com.lion.call.call_for_receiver

sealed class CallReceiverEvent {
    data class Init(val accessToken: String) : CallReceiverEvent()


}