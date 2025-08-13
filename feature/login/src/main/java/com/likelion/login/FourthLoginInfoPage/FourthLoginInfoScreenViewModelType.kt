package com.likelion.login.FourthLoginInfoPage

import kotlinx.coroutines.flow.StateFlow

// Preview 및 Composable에서 사용할 인터페이스
interface FourthLoginInfoScreenViewModelType {
    val bioText: StateFlow<String>
    val isButtonEnabled: StateFlow<Boolean>

    fun onBioTextChanged(newText: String)
}