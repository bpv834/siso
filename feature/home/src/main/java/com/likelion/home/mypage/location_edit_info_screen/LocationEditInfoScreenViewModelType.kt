package com.likelion.home.mypage.location_edit_info_screen

import androidx.compose.runtime.mutableIntStateOf
import com.likelion.domain.mypage.LocationState
import com.likelion.domain.mypage.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface LocationEditInfoScreenViewModelType {
    val topLocation: StateFlow<Location>
    val bottomLocation:StateFlow<Location>
    val currentLocation :StateFlow<String>
    val locationState: StateFlow<LocationState>
    fun setBottomLocation(input: String)
    fun locationComplete(input: String, nav :(String)-> Unit)
    fun fetchUserLocation()
    fun setLocation(string:String)
}