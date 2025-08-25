package com.likelion.home.mypage.location_edit_info_screen

import com.likelion.domain.mypage.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface LocationEditInfoScreenViewModelType {
    val topLocation: StateFlow<Location>
    val bottomLocation:StateFlow<Location>
    fun setBottomLocation(input: String)
    fun locationComplete(input: String, nav :(String)-> Unit)
}