package com.likelion.home.mypage.location_edit_info_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeLocationEditInfoScreenViewModel(
    // usecase자리
): LocationEditInfoScreenViewModelType {
    private val _locationState = MutableStateFlow("")
    override fun locationComplete(input: String, nav :()-> Unit){
        _locationState.update { input }
        if (_locationState.value.isNotBlank()){
            nav()
        }
    }
}