package com.lion.call.call_for_caller

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CallForCallerScreenViewModelType {
    // 1. UI 상태
    // 모든 UI 상태를 담는 단일 StateFlow
    val uiState: StateFlow<CallUiState>
    val uiEvent : SharedFlow<CallUiEvent>

    // 2. 사용자 액션
    fun onClickCall( receiverId: Long) // 발신자가 통화를 시작하는 액션
    fun onClickEndCall() // 통화 종료 액션
    fun onClickBackButton() // 통화 화면에서 뒤로가기 액션
    fun toggleMute() // 음소거/음소거 해제 토글
    fun toggleSpeaker() // 스피커폰/이어폰 모드 토글
    fun resetCallState() // 통화 실패 상태를 초기화하는 함수
    fun onClickReportButton()
    fun onClickConfirmPopup()

    // 3. 타이머 관련 멤버
    fun startCallTimer()
    fun stopCallTimer()


    // 임시 채팅방생성
    fun createChatRoom()
}