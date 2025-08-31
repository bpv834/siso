package com.likelion.home.home_page

import com.likelion.domain.home.model.UsersModel
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModelType {
    val uiState: StateFlow<HomeScreenUiState>

    fun onEvent(event: HomeScreenUiEvent)
    fun resetCallState()
    fun onClickCallButton(receiverId: Long)
}