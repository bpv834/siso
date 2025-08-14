package com.likelion.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThirdLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    // 바텀 시트의 표시 여부를 관리하는 StateFlow
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()
    // 바텀 시트 여는 메서드
    fun showPhotoUploadBottomSheet() {
        _showBottomSheet.value = true
    }
    // 바텀 시트 닫는 메서드
    fun hidePhotoUploadBottomSheet() {
        _showBottomSheet.value = false
    }
}