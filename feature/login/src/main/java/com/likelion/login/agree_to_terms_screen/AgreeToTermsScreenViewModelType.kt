package com.likelion.login.agree_to_terms_screen

import kotlinx.coroutines.flow.StateFlow

interface AgreeToTermsScreenViewModelType {
    val agreeContinueBoolean : StateFlow<Boolean>
    fun agreeContinueBooleanUpdate(agreeStates: Boolean)
}