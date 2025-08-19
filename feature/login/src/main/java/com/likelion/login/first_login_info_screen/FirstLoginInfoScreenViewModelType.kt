package com.likelion.login.first_login_info_screen

import kotlinx.coroutines.flow.StateFlow

interface FirstLoginInfoScreenViewModelType {
    val nameState : String
    val ageState: String
    val fistContinueBoolean : StateFlow<Boolean>
    val myRadioButtons : MutableList<Pair<String, Boolean>>
    val pairRadioButtons : MutableList<Pair<String, Boolean>>
    fun nameUpdate(input: String)
    fun ageUpdate(input: String)
    fun fistContinueBooleanUpdate()
}