package com.likelion.login.login_input_info

import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.usecase.SaveTemporaryUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstLoginInfoScreenViewModel @Inject constructor(
    val saveTemporaryUserProfileUseCase: SaveTemporaryUserProfileUseCase
    // usecase자리
): ViewModel(), FirstLoginInfoScreenViewModelType {

    private val _nameState = MutableStateFlow("")
    override val nameState : String get() = _nameState.value
    private val _ageState = MutableStateFlow("")
    override val ageState: String get() = _ageState.value
    private val _firstContinueBoolean = MutableStateFlow(false)
    override val fistContinueBoolean : StateFlow<Boolean> get() = _firstContinueBoolean.asStateFlow()
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
        input
    }

    override fun ageUpdate(input: String) = _ageState.update {
        input
    }

    override fun fistContinueBooleanUpdate() = _firstContinueBoolean.update {
        val textBoolean = nameState.isNotBlank() && ageState.isNotBlank()
        d("boolean","text $ageState")
        d("boolean","text $nameState")
        d("boolean","text $textBoolean")

        val tempBoolean = myRadioButtons.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","tempBoolean $pair")
            boolean
        }.second && textBoolean
        val continueBoolean = pairRadioButtons.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","continueBoolean $pair")
            boolean
        }.second && tempBoolean
        d("boolean","continueBoolean $continueBoolean")
        continueBoolean
    }

    override fun inputUserInfo(nick : String, age : Int, sex : String, preSex : String){
        // 1. UserSignUpProfile 객체 생성 및 속성 설정
        val user = UserSignUpProfile(nickname = nick,age=age, gender = sex, preferenceSex = preSex)
        viewModelScope.launch {
            saveTemporaryUserProfileUseCase.execute(user)
        }
    }
}