package com.lion.call.call_for_caller

import com.likelion.domain.home.model.UsersModel

// CallUiState: UI에 필요한 모든 데이터를 담는 단일 클래스
// UI에 필요한 모든 상태를 담는 데이터 클래스
data class CallUiState(
    val myUser: UsersModel? = null,
    val otherUser: UsersModel? = null,
    val callProgressState: CallForCallerState = CallForCallerState.Idle,
    val callDuration: Int = 70,
    val isMuted: Boolean = false,
    val isSpeakerOn: Boolean = false,
    val errorMessage: String? = null,
    val isLoading : Boolean = false,
    val isOpenReportSheet : Boolean = false,
    val isOpenConfirmPopup : Boolean = false,
)