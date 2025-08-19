package com.lion.mypage.essential_edit_info_screen

import kotlinx.coroutines.flow.StateFlow

interface EssentialEditInfoScreenViewModelType {
    val nameState : StateFlow<String>
    val ageState: StateFlow<String>
    val introduceState: StateFlow<String>
    val fistContinueBoolean : StateFlow<Boolean>
    fun nameUpdate(input: String)
    fun ageUpdate(input: String)
    fun introduceUpdate(input:String)
    fun fistContinueBooleanUpdate(nameNotBlank:Boolean,ageNotBlank: Boolean)
}