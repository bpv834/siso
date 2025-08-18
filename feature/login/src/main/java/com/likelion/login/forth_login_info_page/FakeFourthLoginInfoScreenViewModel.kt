package com.likelion.login.forth_login_info_page

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeFourthLoginInfoScreenViewModel : FourthLoginInfoScreenViewModelType {

    private val _bioText = MutableStateFlow("")
    override val bioText: StateFlow<String> = _bioText.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(true)
    override val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled.asStateFlow()

    override fun onBioTextChanged(newText: String) {
        // Preview에서는 상태만 바꾸기
        _bioText.value = newText
        _isButtonEnabled.value = newText.length in 5..50
    }
}