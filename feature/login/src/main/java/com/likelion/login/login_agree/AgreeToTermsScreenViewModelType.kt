package com.likelion.login.login_agree

import kotlinx.coroutines.flow.StateFlow

interface AgreeToTermsScreenViewModelType {
    val agreeContinueBoolean : StateFlow<Boolean>
    fun agreeContinueBooleanUpdate(agreeStates: Boolean)
}