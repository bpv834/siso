package com.likelion.login.state

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object SuccessUploadProfile : UiState()
    object SuccessUploadFcmToken : UiState()

    data class Error(val message: String) : UiState()
}