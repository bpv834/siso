package com.likelion.home.mypage.location_edit_info_screen

import android.R.id.input
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LocationEditInfoScreenViewModel @Inject constructor (
    //usecase자리
): ViewModel(), LocationEditInfoScreenViewModelType {
    private val _locationState = MutableStateFlow("")
    override fun locationComplete(input: String, nav :()-> Unit){
        _locationState.update { input }
        if (_locationState.value.isNotBlank()){
            nav()
        }
    }
}