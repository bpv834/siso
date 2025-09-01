package com.likelion.home.mypage.main_edit_info_screen

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.ui.component.photo_layout.EditableImage
import kotlinx.coroutines.flow.StateFlow

interface MainEditInfoScreenViewModelType {
    val uiState: StateFlow<EditUiState>
    val userImages: SnapshotStateList<EditableImage>
    fun fistContinueBooleanUpdate()
    fun nameUpdate(input: String)
    fun playAudio(context: Context)
    fun myRadioButtonsUpdate(sex: String)
    fun pairRadioButtonsUpdate(pair: String)
    fun stopAudio()
    fun runPlayingTimer()
    fun updateUsers(usersModel: UsersFullModel)
}