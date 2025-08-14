package com.likelion.login.event

sealed interface LoginEvent {
    data object ClickLogin : LoginEvent
}