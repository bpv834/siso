package com.likelion.login.agree_to_terms_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface AgreeToTermsScreenViewModelType {
    val agreeContinueBoolean : Boolean
    val agreesBoolean : MutableList<Pair<String, Boolean>>
    fun agreeContinueBooleanUpdate(index:Int)
}