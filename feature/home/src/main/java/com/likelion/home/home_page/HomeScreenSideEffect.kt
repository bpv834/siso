package com.likelion.home.home_page

// Composable에서 한 번만 처리할 부수 효과 (SharedFlow로 관리)
sealed class HomeScreenSideEffect {
    data class NavigateToCaller(val otherUserId: Long) : HomeScreenSideEffect()
    data class ShowSnackbar(val message: String) : HomeScreenSideEffect()
}