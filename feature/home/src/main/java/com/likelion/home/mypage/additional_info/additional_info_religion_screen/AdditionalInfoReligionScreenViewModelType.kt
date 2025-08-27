package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import com.likelion.home.navigation.Pub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AdditionalInfoReligionScreenViewModelType {
    val receiver : StateFlow<String>
    fun updateReceiver(religion : String,nav:(String)->Unit)
}