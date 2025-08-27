package com.likelion.home.mypage.matching_edit_info_screen

import kotlinx.coroutines.flow.StateFlow

interface MatchingEditInfoScreenViewModelType {
    val receiverList : StateFlow<List<String>>
}