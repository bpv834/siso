package com.likelion.home.mypage

import androidx.lifecycle.ViewModel
import com.likelion.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MyPageViewModelType
{
    val uiState : StateFlow<MyPageUiState>
}