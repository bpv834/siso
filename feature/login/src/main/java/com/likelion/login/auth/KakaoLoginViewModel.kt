package com.likelion.login.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(
    private val fetchKakaoTokenUseCase: FetchKakaoTokenUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow(false)
    val loginState get() = _loginState

    fun kakaoLogin() {
        viewModelScope.launch {
            fetchKakaoTokenUseCase
        }
    }
}