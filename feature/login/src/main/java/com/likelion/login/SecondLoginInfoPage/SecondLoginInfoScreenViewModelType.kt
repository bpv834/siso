package com.likelion.login.SecondLoginInfoPage

import kotlinx.coroutines.flow.StateFlow

interface SecondLoginInfoScreenViewModelType {
    val selectedInterests: StateFlow<Set<String>>
    val isPossibleNextState : StateFlow<Boolean>
    fun onClickToggle(interests : String)
    fun onClickNextButton()
}