package com.likelion.login.SecondLoginInfoPage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SecondLoginInfoScreenViewModel@Inject constructor(
    // usecase
) : ViewModel(), SecondLoginInfoScreenViewModelType{
    private val _selectedInterests = MutableStateFlow<Set<String>>(emptySet())
    override val selectedInterests: StateFlow<Set<String>> = _selectedInterests.asStateFlow()

    private val _isPossibleNextState = MutableStateFlow<Boolean>(false) // 3이상이면 트루
    override val isPossibleNextState: StateFlow<Boolean> = _isPossibleNextState.asStateFlow()

    override fun onClickToggle(interest: String) {
        val currentInterests = _selectedInterests.value.toMutableSet()
        if (currentInterests.contains(interest)) {
            currentInterests.remove(interest)
        } else {
            currentInterests.add(interest)
        }
        _selectedInterests.value = currentInterests.toSet()
        _isPossibleNextState.value =if(_selectedInterests.value.size>=3)  true else false
    }

    override fun onClickNextButton() {
        // 다음 화면으로 이동하는 로직
    }
}