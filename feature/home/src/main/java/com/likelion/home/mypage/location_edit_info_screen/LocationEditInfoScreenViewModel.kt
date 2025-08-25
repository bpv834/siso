package com.likelion.home.mypage.location_edit_info_screen

import android.R.id.input
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LocationEditInfoScreenViewModel @Inject constructor (
    // usecase자리
    private val topLocationUseCase: TopLocationUseCase,
    private val bottomLocationUseCase: BottomLocationUseCase
): ViewModel(), LocationEditInfoScreenViewModelType {
    private val _locationState = MutableStateFlow("")
    private val _topLocation = MutableStateFlow(topLocationUseCase.invoke())
    val topLocation = _topLocation.asStateFlow()

    override fun locationComplete(input: String, nav :()-> Unit){
        _locationState.update { input }
        if (_locationState.value.isNotBlank()){
            nav()
        }
    }
}