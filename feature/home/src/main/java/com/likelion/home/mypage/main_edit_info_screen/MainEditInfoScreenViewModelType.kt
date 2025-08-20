package com.likelion.home.mypage.main_edit_info_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface MainEditInfoScreenViewModelType {
    val myRadioButtons: StateFlow<MutableList<Pair<String, Boolean>>>
    val pairRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>>
    val fistContinueBoolean : StateFlow<Boolean>
    val nameState : StateFlow<String>
    val ageState: StateFlow<String>
    val heightState : StateFlow<String>
    val weightState: StateFlow<String>
    fun fistContinueBooleanUpdate()
    fun nameUpdate(input: String)

}