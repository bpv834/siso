package com.likelion.login.FourthLoginInfoPage

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
    // usecase
): ViewModel(), FourthLoginInfoScreenViewModelType {

    private val _bioText = MutableStateFlow("")
    override val bioText: StateFlow<String> = _bioText.asStateFlow()

    override val isButtonEnabled: StateFlow<Boolean> = bioText.map { currentText ->
        currentText.length in 5..50
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun onBioTextChanged(newText: String) {
        if (newText.length <= 50) {
            _bioText.value = newText
        }
    }
}