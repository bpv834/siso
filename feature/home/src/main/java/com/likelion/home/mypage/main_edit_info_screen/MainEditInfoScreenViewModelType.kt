package com.likelion.home.mypage.main_edit_info_screen

import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.mypage.model.UsersFullModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface MainEditInfoScreenViewModelType {
    val uiState: StateFlow<EditUiState>
    fun fistContinueBooleanUpdate()
    fun nameUpdate(input: String)
    fun playAudio(filePath: String)
    fun myRadioButtonsUpdate(sex: String)
    fun pairRadioButtonsUpdate(pair: String)
    fun stopAudio()
    fun runPlayingTimer()
}