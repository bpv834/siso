package com.likelion.home.mypage

import kotlinx.coroutines.flow.StateFlow

interface MyPageViewModelType
{
    val uiState : StateFlow<MyPageUiState>
}