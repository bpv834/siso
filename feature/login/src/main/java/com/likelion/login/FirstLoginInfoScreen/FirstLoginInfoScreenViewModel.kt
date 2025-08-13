package com.likelion.login.FirstLoginInfoScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FirstLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel() {
    private val _nameState = MutableStateFlow("")
    val nameState : String get() = _nameState.value
    private val _firstContinueBoolean = MutableStateFlow(true)
    val fistContinueBoolean : Boolean get() = _firstContinueBoolean.value
    private val _myRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "남성", second = true),
            Pair(first = "여성", second = false),
        )
    )
    val myRadioButtons : MutableList<Pair<String, Boolean>> get() = _myRadioButtons.value
    private val _pairRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "동성", second = true),
            Pair(first = "이성", second = false),
        )
    )
    val pairRadioButtons : MutableList<Pair<String, Boolean>> get() = _pairRadioButtons.value
    fun nameUpdate(input: String) = _nameState.update {
        it + input
    }

    fun fistContinueBooleanUpdate(input: Boolean) = _firstContinueBoolean.update { input }
}