package com.likelion.home.mypage.main_edit_info_screen

import android.media.MediaPlayer
import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import com.likelion.domain.mypage.model.UsersFullModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

data class EditUiState(
    val mediaPlayer : MediaPlayer = MediaPlayer(),
    val myRadioButtons: List<Pair<String, Boolean>> = listOf(
        Pair(first = "여성", second = false),
        Pair(first = "남성", second = false),
    ),
    val pairRadioButtons : List<Pair<String, Boolean>> = listOf(
        Pair(first = "여성", second = false),
        Pair(first = "남성", second = false),
        Pair(first = "상관없음", second = false),
    ),
    val playTime : Int = 0,
    val voicePath :String = "",
    val playJob : Job? = null,
    val playState : Boolean = false,

    val firstContinueBoolean : Boolean = false,
    val receiverUsersModel: UsersFullModel? = null,
    val editUsersModel: UsersFullModel = UsersFullModel(
        id = 0,
        userImages = "",
        nickname = "",
        age = -1,
        voiceUrl = "",
        introduce = "",
        sex = "",
        preferenceSex = "",
        location = "",
        drinkingCapacity = "",
        religion = "",
        isSmoke = "",
        interests = listOf(),
        mbti = "",
        meeting = listOf()
    )
){
    fun fistContinueBooleanUpdate() : Boolean =
        editUsersModel.nickname.isNotBlank() && editUsersModel.age == -1 &&
                myRadioButtons.reduce { acc, pair ->
                    val boolean = acc.copy(second = pair.second || acc.second)
                    d("boolean", "fistContinueBooleanUpdate $pair")
                    boolean
                }.second &&
                pairRadioButtons.reduce { acc, pair ->
                    val boolean = acc.copy(second = pair.second || acc.second)
                    d("boolean", "continueBoolean $pair")
                    boolean
                }.second
}