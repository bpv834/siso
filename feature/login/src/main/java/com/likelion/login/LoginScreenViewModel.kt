package com.likelion.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import com.likelion.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val fetchKakaoTokenUseCase: FetchKakaoTokenUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun login() {
        viewModelScope.launch {
            val token = fetchKakaoTokenUseCase()
            _uiState.update { current ->
                current.copy(
                    isLoggedIn = token != null,
                    kakaoToken = token
                )
            }
        }
    }

    fun 백엔드통신(token: String) {


    }
}