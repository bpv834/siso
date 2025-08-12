package com.likelion.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThirdLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    val 변수명 = MutableStateFlow(true).asStateFlow()
}