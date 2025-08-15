package com.likelion.login.first_loginInfo_screen

interface FirstLoginInfoScreenViewModelType {
    val nameState : String
    val ageState: String
    val fistContinueBoolean : Boolean
    val myRadioButtons : MutableList<Pair<String, Boolean>>
    val pairRadioButtons : MutableList<Pair<String, Boolean>>
    fun nameUpdate(input: String)
    fun ageUpdate(input: String)
    fun fistContinueBooleanUpdate(input: Boolean)
}