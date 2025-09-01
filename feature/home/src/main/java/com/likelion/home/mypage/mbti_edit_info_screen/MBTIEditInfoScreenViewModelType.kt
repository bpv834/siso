package com.likelion.home.mypage.mbti_edit_info_screen

import kotlinx.coroutines.flow.StateFlow

interface MBTIEditInfoScreenViewModelType {
    val receiver : StateFlow<String>
    val nothing :String
    fun setReceiver(receiver: String)
}