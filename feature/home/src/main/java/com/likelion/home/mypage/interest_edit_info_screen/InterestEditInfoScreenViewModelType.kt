package com.likelion.home.mypage.interest_edit_info_screen

import kotlinx.coroutines.flow.StateFlow

interface InterestEditInfoScreenViewModelType {
    val receiverList : StateFlow<List<String>>
}