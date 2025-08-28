package com.likelion.home.mypage.main_edit_info_screen

import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.mypage.model.UsersFullModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface MainEditInfoScreenViewModelType {
    val myRadioButtons: StateFlow<MutableList<Pair<String, Boolean>>>
    val pairRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>>
    val fistContinueBoolean : StateFlow<Boolean>
    val potoState : StateFlow<String>
    val voiceState : StateFlow<String>
    val nameState : StateFlow<String>
    val ageState: StateFlow<String>
    val introduceState: StateFlow<String>
    val receiverUsersModel: StateFlow<UsersFullModel?>
    val usersModel: StateFlow<UsersFullModel?>
    fun fistContinueBooleanUpdate()
    fun nameUpdate(input: String)
    fun playAudio(filePath: String)
    fun stopAudio()
}