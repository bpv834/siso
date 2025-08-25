package com.likelion.home.mypage.location_edit_info_screen

import androidx.lifecycle.ViewModel
import com.likelion.domain.mypage.usecase.BottomLocationUseCaseImpl
import com.likelion.domain.mypage.usecase.TopLocationUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeLocationEditInfoScreenViewModel (
    // usecase자리
    private val topLocationUseCaseImpl: TopLocationUseCaseImpl,
    private val bottomLocationUseCaseImpl: BottomLocationUseCaseImpl
): LocationEditInfoScreenViewModelType {
    private val _locationState = MutableStateFlow("")
    private val _topLocation = MutableStateFlow(topLocationUseCaseImpl.invoke())
    val topLocation = _topLocation.asStateFlow()
    override fun locationComplete(input: String, nav :()-> Unit){
        _locationState.update { input }
        if (_locationState.value.isNotBlank()){
            nav()
        }
    }
}