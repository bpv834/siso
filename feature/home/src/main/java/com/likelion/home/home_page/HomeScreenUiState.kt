package com.likelion.home.home_page

import com.likelion.domain.home.model.UsersModel

// UI가 가질 수 있는 모든 상태
sealed class HomeScreenUiState {
    object LoadingToken : HomeScreenUiState()
    object LoadingUsers : HomeScreenUiState()
    data class Success(val users: List<UsersModel>) : HomeScreenUiState()
    data class Error(val message: String) : HomeScreenUiState()
}
