package com.likelion.login.login_input_info

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
    fun inputUserInfo(nick : String, age : Int, sex : String, preSex : String)
}