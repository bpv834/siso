package com.likelion.home.mypage.additional_info.additional_info_smoking_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoSmokingScreenViewModel@Inject constructor (

    //usecase자리
): ViewModel(), AdditionalInfoSmokingScreenViewModelType {
    private var _receiver = MutableStateFlow("")

    override val receiver: StateFlow<String> = _receiver.asStateFlow()

    fun fetch(smoking : String){
        _receiver.update { smoking }
    }
}