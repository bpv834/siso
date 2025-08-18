package com.lion.mypage.first_edit_info_screen

interface FirstEditInfoScreenViewModelType {
    val nameState : String
    val ageState: String
    val fistContinueBoolean : Boolean
    val myRadioButtons : MutableList<Pair<String, Boolean>>
    val pairRadioButtons : MutableList<Pair<String, Boolean>>
    fun nameUpdate(input: String)
    fun ageUpdate(input: String)
    fun fistContinueBooleanUpdate(input: Boolean)
}