package com.likelion.login.login_input_hobby

import kotlinx.coroutines.flow.StateFlow

interface SecondLoginInfoScreenViewModelType {
    val selectedInterests: StateFlow<Set<String>>
    val isPossibleNextState : StateFlow<Boolean>
    fun onClickToggle(interests : String)
    fun onClickNextButton()
}