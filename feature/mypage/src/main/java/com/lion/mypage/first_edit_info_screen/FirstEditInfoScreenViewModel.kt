package com.lion.mypage.first_edit_info_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FirstEditInfoScreenViewModel @Inject constructor(

    // usecase자리
): ViewModel(), FirstEditInfoScreenViewModelType {
    private val _nameState = MutableStateFlow("")
    override val nameState : String get() = _nameState.value
    private val _ageState = MutableStateFlow("")
    override val ageState: String get() = _ageState.value
    private val _firstContinueBoolean = MutableStateFlow(true)
    override val fistContinueBoolean : Boolean get() = _firstContinueBoolean.value
    private val _myRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "남성", second = true),
            Pair(first = "여성", second = false),
        )
    )
    override val myRadioButtons : MutableList<Pair<String, Boolean>> get() = _myRadioButtons.value
    private val _pairRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "동성", second = true),
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

    override fun fistContinueBooleanUpdate(input: Boolean) = _firstContinueBoolean.update { input }
}