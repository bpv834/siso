package com.likelion.login.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import com.likelion.domain.login.usecase.GetAuthTokenUseCase
import com.likelion.domain.login.usecase.SaveAuthTokenUseCase
import com.likelion.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val fetchKakaoTokenUseCase: FetchKakaoTokenUseCase,
    private val saveAuthTokenUseCase: SaveAuthTokenUseCase,
    private val getAuthTokenUseCase: GetAuthTokenUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun login() {
        viewModelScope.launch {
            when (val result = fetchKakaoTokenUseCase()) {
                is KakaoTokenResult.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            isLoggedIn = true,
                            kakaoToken = result.token,
                            error = null
                        )
                    }
                    saveAuthTokenUseCase(result.token)
                    Log.d("viewModel", result.token)
                    val a = getAuthTokenUseCase().firstOrNull()
                    Log.d("Token", a ?: "없음")
                }

                KakaoTokenResult.Canceled -> {
                    _uiState.update { current ->
                        current.copy(
                            isLoggedIn = false,
                            kakaoToken = null,
                            error = "로그인이 취소되었습니다."
                        )
                    }
                    Log.d("viewModel", "Canceled")
                }

                is KakaoTokenResult.Error -> {
                    _uiState.update { current ->
                        current.copy(
                            isLoggedIn = false,
                            kakaoToken = null,
                            error = result.cause.message ?: "알 수 없는 오류"
                        )
                    }
                    Log.d("viewModel", "Error")
                    Log.d("viewModel", "${_uiState.value.error}")
                }
            }

        }
    }
}