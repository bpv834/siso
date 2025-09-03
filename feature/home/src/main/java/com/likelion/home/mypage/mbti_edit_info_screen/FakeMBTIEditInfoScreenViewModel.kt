package com.likelion.home.mypage.mbti_edit_info_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeMBTIEditInfoScreenViewModel(
    // usecase자리
): MBTIEditInfoScreenViewModelType {
    private var _receiver = MutableStateFlow("")
    override val nothing :String = "|||"
    override fun setReceiver(receiver: String) {
        _receiver.update {
            receiver.ifBlank { nothing }
        }
    }

    override val receiver: StateFlow<String> = _receiver.asStateFlow()
}