package com.likelion.login.agree_to_terms_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface AgreeToTermsScreenViewModelType {
    val agreeContinueBoolean : StateFlow<Boolean>
    fun agreeContinueBooleanUpdate(agreeStates: Boolean)
}