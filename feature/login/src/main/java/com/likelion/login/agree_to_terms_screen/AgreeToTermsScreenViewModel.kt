package com.likelion.login.agree_to_terms_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AgreeToTermsScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    private val _agreeContinueBoolean = MutableStateFlow(false)
    val agreeContinueBoolean : StateFlow<Boolean> get() = _agreeContinueBoolean.asStateFlow()
    private val _agreesBoolean = MutableStateFlow(
        mutableListOf(
            Pair("(필수) 이용약관 동의",false),
            Pair("(선택) 마케팅 정보 수신",false),
        )
    )
    val agreesBoolean : StateFlow<MutableList<Pair<String, Boolean>>> get() = _agreesBoolean.asStateFlow()

    fun agreeContinueBooleanUpdate() = _agreeContinueBoolean.update {
        var agree = true
        agreesBoolean.value.forEach {
            agree = agree&&it.second
        }
        agree
    }

    fun requiredTermsAgreeBooleanUpdate() = _agreeContinueBoolean.update { !it }

    fun receptionAgreeBooleanUpdate() = _agreeContinueBoolean.update { !it }

    fun requiredTermsAgreeContinue() = {
        requiredTermsAgreeBooleanUpdate()
        agreeContinueBooleanUpdate()
    }

    fun receptionAgreeContinue() = {
        receptionAgreeBooleanUpdate()
        agreeContinueBooleanUpdate()
    }

}