package com.likelion.home.mypage.additional_info.additional_info_smoking_screen

import kotlinx.coroutines.flow.StateFlow

interface AdditionalInfoSmokingScreenViewModelType {
    val receiver : StateFlow<String>
}