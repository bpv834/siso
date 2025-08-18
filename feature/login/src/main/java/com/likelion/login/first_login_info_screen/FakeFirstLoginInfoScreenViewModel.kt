package com.likelion.login.first_login_info_screen

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeFirstLoginInfoScreenViewModel(
    // usecase자리
): FirstLoginInfoScreenViewModelType {
    private val _nameState = MutableStateFlow("")
    override val nameState : String get() = _nameState.value
    private val _ageState = MutableStateFlow("")
    override val ageState: String get() = _ageState.value
    private val _firstContinueBoolean = MutableStateFlow(false)
    override val fistContinueBoolean : Boolean get() = _firstContinueBoolean.value
    private val _myRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "여성", second = false),
            Pair(first = "남성", second = false),
        )
    )
    override val myRadioButtons : MutableList<Pair<String, Boolean>> get() = _myRadioButtons.value
    private val _pairRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "동성", second = false),
            Pair(first = "이성", second = false),
        )
    )
    override val pairRadioButtons : MutableList<Pair<String, Boolean>> get() = _pairRadioButtons.value
    override fun nameUpdate(input: String) = _nameState.update {
        it + input
    }

    override fun ageUpdate(input: String) = _nameState.update {
        it + input
    }

    override fun fistContinueBooleanUpdate() = _firstContinueBoolean.update {
        val textBoolean = nameState.isNotBlank() && ageState.isNotBlank()
        val tempBoolean = myRadioButtons.reduce { acc, pair ->
            acc.copy(second = pair.second || textBoolean)
        }.second
        pairRadioButtons.reduce { acc, pair ->
            acc.copy(second = pair.second || tempBoolean)
        }.second
    }
}