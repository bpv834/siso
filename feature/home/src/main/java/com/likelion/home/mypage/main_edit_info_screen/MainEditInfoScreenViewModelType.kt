package com.likelion.home.mypage.main_edit_info_screen

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

interface MainEditInfoScreenViewModelType {
    val uiState: StateFlow<EditUiState>
    fun fistContinueBooleanUpdate()
    fun nameUpdate(input: String)
    fun playAudio(context: Context)
    fun myRadioButtonsUpdate(sex: String)
    fun pairRadioButtonsUpdate(pair: String)
    fun stopAudio()
    fun runPlayingTimer()
}