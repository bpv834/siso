package com.likelion.login.agree_to_terms_screen

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAgreeToTermsScreenViewModel (
    // usecase자리
):AgreeToTermsScreenViewModelType {
    private val _agreeContinueBoolean = MutableStateFlow(false)
    override val agreeContinueBoolean: StateFlow<Boolean> get() = _agreeContinueBoolean.asStateFlow()

    override fun agreeContinueBooleanUpdate(agreeStates: Boolean) = _agreeContinueBoolean.update {
        agreeStates
    }


}