package com.likelion.home.mypage.additional_info.additional_info_smoking_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAdditionalInfoSmokingScreenViewModel(
    // usecase자리
): AdditionalInfoSmokingScreenViewModelType {
    private var _receiver = MutableStateFlow("")
    init {
        _receiver.update {
            it// 받아올 값 추가
        }
    }
    override val receiver: StateFlow<String> = _receiver.asStateFlow()
}