package com.likelion.home.home_page

// 사용자 액션 및 일회성 이벤트를 위한 클래스
sealed class HomeScreenUiEvent {
    object GetTokenAndLoadUsers : HomeScreenUiEvent()
    data class OnClickCallButton(val receiverId: Long) : HomeScreenUiEvent()
    data class ChangeDialogStatus(val isDialog: Boolean) : HomeScreenUiEvent()
    object GetDialogStatus : HomeScreenUiEvent()
    data class PermissionChanged(val isGranted: Boolean) : HomeScreenUiEvent()
}