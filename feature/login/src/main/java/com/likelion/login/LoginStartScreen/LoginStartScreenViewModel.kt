package com.likelion.login.LoginStartScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginStartScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    private val _fistContinueBoolean = MutableStateFlow(true)
    val fistContinueBoolean : Boolean get() = _fistContinueBoolean.value

    fun fistContinueBooleanUpdate(input: Boolean) = _fistContinueBoolean.update { input }
}