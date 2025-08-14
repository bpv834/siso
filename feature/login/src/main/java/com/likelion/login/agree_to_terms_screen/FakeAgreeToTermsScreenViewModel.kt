package com.likelion.login.agree_to_terms_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeAgreeToTermsScreenViewModel (
    // usecase자리
):AgreeToTermsScreenViewModelType {
    private val _agreeContinueBoolean = MutableStateFlow(false)
    override val agreeContinueBoolean: Boolean get() = _agreeContinueBoolean.value
    private val _agreesBoolean = MutableStateFlow(
        mutableListOf(
            Pair("(필수) 이용약관 동의", false),
            Pair("(선택) 마케팅 정보 수신", false),
        )
    )
    override val agreesBoolean: MutableList<Pair<String, Boolean>> get() = _agreesBoolean.value

    override fun agreeContinueBooleanUpdate(index: Int) = _agreeContinueBoolean.update {
        val term = agreesBoolean[index]
        agreesBoolean[index] = term.copy(
            term.first, !term.second
        )
        var agree = true
        agreesBoolean.forEach {
            agree = agree && it.second
        }
        agree
    }
}


