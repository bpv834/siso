package com.likelion.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FourthLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    // 사용자 자기소개 텍스트 상태
    private val _bioText = MutableStateFlow("")
    val bioText: StateFlow<String> = _bioText.asStateFlow()

    // 자기소개 텍스트 길이에 따른 버튼 활성화 여부 (5자 이상, 50자 이하)
    val isButtonEnabled: StateFlow<Boolean> = bioText.map { currentText ->
        currentText.length in 5..50
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), // 구독이 사라진 후 5초간 캐시 유지
        false // 초기 값
    )

    fun onBioTextChanged(newText: String) {
        // 50자 이상 입력되지 않도록 제한
        if (newText.length <= 50) {
            _bioText.value = newText
        }
    }
}