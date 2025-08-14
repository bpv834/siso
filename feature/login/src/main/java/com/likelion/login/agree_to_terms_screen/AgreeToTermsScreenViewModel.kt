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
): ViewModel(), AgreeToTermsScreenViewModelType {
    private val _agreeContinueBoolean = MutableStateFlow(false)
    override val agreeContinueBoolean : Boolean get() = _agreeContinueBoolean.value
    private val _agreesBoolean = MutableStateFlow(
        mutableListOf(
            Pair("(필수) 이용약관 동의",false),
            Pair("(선택) 마케팅 정보 수신",false),
        )
    )
    override val agreesBoolean : MutableList<Pair<String, Boolean>> get() = _agreesBoolean.value

    override fun agreeContinueBooleanUpdate(index:Int) = _agreeContinueBoolean.update {
        val term = agreesBoolean[index]
        agreesBoolean[index] = term.copy(
            term.first,!term.second
        )
        var agree = true
        agreesBoolean.forEach {
            agree = agree&&it.second
        }
        agree
    }


}