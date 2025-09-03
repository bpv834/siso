package com.likelion.home.mypage.additional_info.additional_info_alcohol_screen

import kotlinx.coroutines.flow.StateFlow

interface AdditionalInfoAlcoholScreenViewModelType {
    val receiver : StateFlow<String>
}