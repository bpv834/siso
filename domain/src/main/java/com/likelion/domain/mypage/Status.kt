package com.likelion.domain.mypage

sealed class LocationState {
    object Loading : LocationState()
    data class Success(val address: String) : LocationState()
    data class Error(val message: String) : LocationState()
}