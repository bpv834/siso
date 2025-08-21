package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AdditionalInfoReligionScreenViewModelType {
    val receiverList : StateFlow<List<String>>
}