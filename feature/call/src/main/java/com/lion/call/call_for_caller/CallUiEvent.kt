package com.lion.call.call_for_caller
sealed class CallUiEvent {
    // 네비게이션 이벤트
    data object NavigateUp : CallUiEvent()

    // 오류 표시 이벤트
    data class ShowError(val message: String) : CallUiEvent()

    // (선택) 토스트
    data class ShowToast(val message: String) : CallUiEvent()

    // (선택) 스낵바
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : CallUiEvent()

    // 신고 이벤트: 신고 대상의 ID를 포함
    data class ShowReportSheet(val targetId: String) : CallUiEvent()
}