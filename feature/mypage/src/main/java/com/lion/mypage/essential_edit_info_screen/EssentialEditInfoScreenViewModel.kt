package com.lion.mypage.essential_edit_info_screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EssentialEditInfoScreenViewModel @Inject constructor(
    // usecase자리
): ViewModel(), EssentialEditInfoScreenViewModelType {
    private val _nameState = MutableStateFlow("")
    override val nameState : StateFlow<String> get() = _nameState.asStateFlow()
    private val _ageState = MutableStateFlow("")
    override val ageState: StateFlow<String> get() = _ageState.asStateFlow()
    private val _introduceState = MutableStateFlow("")
    override val introduceState: StateFlow<String> get() = _introduceState.asStateFlow()
    private val _firstContinueBoolean = MutableStateFlow(false)
    override val fistContinueBoolean : StateFlow<Boolean> get() = _firstContinueBoolean.asStateFlow()
    override fun nameUpdate(input: String) = _nameState.update {
        input
    }

    override fun ageUpdate(input: String) = _ageState.update {
        input
    }

    override fun introduceUpdate(input: String) = _ageState.update {
        input
    }

    override fun fistContinueBooleanUpdate(nameNotBlank:Boolean,ageNotBlank: Boolean) = _firstContinueBoolean.update {
        val textBoolean = nameNotBlank && ageNotBlank
        d("boolean","text $ageState")
        d("boolean","text $nameState")
        d("boolean","text $textBoolean")

        textBoolean
    }
}