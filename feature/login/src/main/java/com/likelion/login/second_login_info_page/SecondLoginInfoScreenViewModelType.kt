package com.likelion.login.second_login_info_page

import kotlinx.coroutines.flow.StateFlow

interface SecondLoginInfoScreenViewModelType {
    val selectedInterests: StateFlow<Set<String>>
    val isPossibleNextState : StateFlow<Boolean>
    fun onClickToggle(interests : String)
    fun onClickNextButton()
}