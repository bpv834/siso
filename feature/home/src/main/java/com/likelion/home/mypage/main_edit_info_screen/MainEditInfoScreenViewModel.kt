package com.likelion.home.mypage.main_edit_info_screen

import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainEditInfoScreenViewModel @Inject constructor (
    //usecase자리
): ViewModel(), MainEditInfoScreenViewModelType {

    init {

    }
    private val _nameState = MutableStateFlow("")
    override val nameState : StateFlow<String> get() = _nameState.asStateFlow()
    private val _ageState = MutableStateFlow("")
    override val ageState: StateFlow<String> get() = _ageState.asStateFlow()
    private val _heightState = MutableStateFlow("")
    override val heightState : StateFlow<String> get() = _heightState.asStateFlow()
    private val _weightState = MutableStateFlow("")
    override val weightState: StateFlow<String> get() = _weightState.asStateFlow()
    private val _myRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "여성", second = false),
            Pair(first = "남성", second = false),
        )
    )
    override val myRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>> get() = _myRadioButtons.asStateFlow()
    private val _pairRadioButtons = MutableStateFlow(
        mutableStateListOf(
            Pair(first = "이성", second = false),
            Pair(first = "동성", second = false),
            Pair(first = "상관없음", second = false),
        )
    )
    override val pairRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>> get() = _pairRadioButtons.asStateFlow()
    private val _firstContinueBoolean = MutableStateFlow(false)
    override val fistContinueBoolean : StateFlow<Boolean> get() = _firstContinueBoolean.asStateFlow()
    override fun fistContinueBooleanUpdate() = _firstContinueBoolean.update {
        val textBoolean = nameState.value.isNotBlank() && ageState.value.isNotBlank()
        d("boolean","text $ageState")
        d("boolean","text $nameState")
        d("boolean","text $textBoolean")

        val tempBoolean = myRadioButtons.value.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","tempBoolean $pair")
            boolean
        }.second && textBoolean
        val continueBoolean = pairRadioButtons.value.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","continueBoolean $pair")
            boolean
        }.second && tempBoolean
        d("boolean","continueBoolean $continueBoolean")
        continueBoolean
    }

    override fun nameUpdate(input: String) {
        _nameState.update { input }
        fistContinueBooleanUpdate()
    }
}