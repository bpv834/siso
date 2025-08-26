package com.likelion.home.mypage.location_edit_info_screen

import android.annotation.SuppressLint
import android.util.Log.d
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.mypage.LocationState
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.CurrentLocationSetUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationEditInfoScreenViewModel @Inject constructor (
    // usecase자리
    private val topLocationUseCase: TopLocationUseCase,
    private val bottomLocationUseCase: BottomLocationUseCase,
    private val currentLocationSetUseCase: CurrentLocationSetUseCase,
): ViewModel(), LocationEditInfoScreenViewModelType {
    private val _completeState = MutableStateFlow("")
    private val _topLocation = MutableStateFlow(topLocationUseCase.invoke())
    override val topLocation = _topLocation.asStateFlow()
    private val _bottomLocation = MutableStateFlow(Location(name = listOf()))
    override val bottomLocation = _bottomLocation.asStateFlow()
    private val _locationState = MutableStateFlow<LocationState>(LocationState.Loading)
    override val locationState: StateFlow<LocationState> = _locationState
    private var _currentLocation = mutableStateOf("")
    override val currentLocation = _currentLocation.value

    override fun setBottomLocation(input: String){
        val inputBottom = if(input != "")
            bottomLocationUseCase.invoke(input)
            else Location(name = listOf())
        _bottomLocation.update { inputBottom }
    }

    override fun locationComplete(input: String, nav: (String) -> Unit) {
        _completeState.update { input }
        if (_completeState.value.isNotBlank()){
            nav(_completeState.value)
        }
    }

    @SuppressLint("TimberArgCount")
    override fun fetchUserLocation() {
        viewModelScope.launch {
            currentLocationSetUseCase.invoke().collectLatest { state ->
                _locationState.value = state
            }
            d("fetchUserLocation","${_locationState.value}")
        }
    }
}
