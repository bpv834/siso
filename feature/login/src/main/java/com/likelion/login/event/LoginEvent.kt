package com.likelion.login.event

sealed interface LoginEvent {
    data object ClickLogin : LoginEvent
    data object CheckLocalToken : LoginEvent
}