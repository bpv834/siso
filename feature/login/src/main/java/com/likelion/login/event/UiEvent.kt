package com.likelion.login.event

sealed class UiEvent {
    object UploadProfile : UiEvent()
    object UploadFcmToken : UiEvent()

}