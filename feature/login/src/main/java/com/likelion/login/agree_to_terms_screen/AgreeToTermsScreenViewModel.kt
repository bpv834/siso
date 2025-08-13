package com.likelion.login.agree_to_terms_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AgreeToTermsScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    private val _agreeContinueBoolean = MutableStateFlow(true)
    val agreeContinueBoolean : Boolean get() = _agreeContinueBoolean.value
    private val _requiredTermsAgreeBoolean = MutableStateFlow(false)
    val requiredTermsAgreeBoolean : Boolean get() = _requiredTermsAgreeBoolean.value
    private val _receptionAgreeBoolean = MutableStateFlow(false)
    val receptionAgreeBoolean : Boolean get() = _receptionAgreeBoolean.value

    fun agreeContinueBooleanUpdate() = _agreeContinueBoolean.update { requiredTermsAgreeBoolean&&receptionAgreeBoolean }

    fun requiredTermsAgreeBooleanUpdate() = _agreeContinueBoolean.update { !it }

    fun receptionAgreeBooleanUpdate() = _agreeContinueBoolean.update { !it }

}