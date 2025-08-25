package com.likelion.home.mypage.location_edit_info_screen

import androidx.lifecycle.ViewModel
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber.Forest.d
import javax.inject.Inject

class FakeLocationEditInfoScreenViewModel (
    // usecase자리
    private val topLocationUseCase: TopLocationUseCase,
    private val bottomLocationUseCase: BottomLocationUseCase
): LocationEditInfoScreenViewModelType {
    private val _locationState = MutableStateFlow("")

    private val _topLocation = MutableStateFlow(topLocationUseCase.invoke())
    override val topLocation = _topLocation.asStateFlow()
    private val _bottomLocation = MutableStateFlow(Location(name = listOf()))
    override val bottomLocation = _bottomLocation.asStateFlow()
    
    init {
        _topLocation.update {
            d("topLocationUseCase.invoke() : ${topLocationUseCase.invoke()}")
            topLocationUseCase.invoke() }
    }

    override fun setBottomLocation(input: String){
        val inputBottom = if(input != "")
            bottomLocationUseCase.invoke(input)
        else Location(name = listOf())
        _bottomLocation.update { inputBottom }
    }

    override fun locationComplete(input: String, nav: (String) -> Unit) {
        _locationState.update { input }
        if (_locationState.value.isNotBlank()){
            nav(_locationState.value)
        }
    }
}
