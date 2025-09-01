package com.likelion.home.mypage.location_edit_info_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.likelion.domain.mypage.LocationState
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber.Forest.d
import javax.inject.Inject

class FakeLocationEditInfoScreenViewModel (
    // usecase자리
    private val topLocationUseCase: TopLocationUseCase,
    private val bottomLocationUseCase: BottomLocationUseCase,
): LocationEditInfoScreenViewModelType {
    private val _completeState = MutableStateFlow("")

    private val _topLocation = MutableStateFlow(topLocationUseCase.invoke())
    override val topLocation = _topLocation.asStateFlow()
    private val _bottomLocation = MutableStateFlow(Location(name = listOf()))
    override val bottomLocation = _bottomLocation.asStateFlow()
    private val _locationState = MutableStateFlow<LocationState>(LocationState.Loading)
    override val locationState: StateFlow<LocationState> = _locationState
    private var _currentLocation = MutableStateFlow("")
    override val currentLocation = _currentLocation.asStateFlow()

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
    override fun setLocation(string:String){
        _currentLocation.update {
            string
        }
    }

    override fun locationComplete(input: String, nav: (String) -> Unit) {
        _completeState.update { input }
        if (_completeState.value.isNotBlank()){
            nav(_completeState.value)
        }
    }
    override fun fetchUserLocation() {

    }
}
