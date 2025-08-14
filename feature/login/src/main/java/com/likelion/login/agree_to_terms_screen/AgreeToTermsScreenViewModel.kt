package com.likelion.login.agree_to_terms_screen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AgreeToTermsScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel(), AgreeToTermsScreenViewModelType {
    private val _agreeContinueBoolean = MutableStateFlow(false)
    override val agreeContinueBoolean : StateFlow<Boolean> get() = _agreeContinueBoolean.asStateFlow()

    override fun agreeContinueBooleanUpdate(agreeStates: Boolean) = _agreeContinueBoolean.update {
        agreeStates
    }


}