package com.likelion.home.mypage.matching_edit_info_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MatchingEditInfoScreenViewModel @Inject constructor (
    //usecase자리
): ViewModel(), MatchingEditInfoScreenViewModelType {
    private var _receiverList = MutableStateFlow(listOf<String>())
    init {
        // 초기 리스트 불러오는 부분
        _receiverList.update {
            listOf<String>()
        }
    }
    override val receiverList = _receiverList.asStateFlow()
}