package com.likelion.home.mypage.interest_edit_info_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeInterestEditInfoScreenViewModel(
    // usecase자리
): InterestEditInfoScreenViewModelType {
    private var _receiverList = MutableStateFlow(listOf<String>())
    init {
        // 초기 리스트 불러오는 부분
        _receiverList.update {
            listOf<String>()
        }
    }
    override val receiverList = _receiverList.asStateFlow()
}