package com.likelion.home.mypage.interest_edit_info_screen

import kotlinx.coroutines.flow.StateFlow

interface InterestEditInfoScreenViewModelType {
    val cultureReceiverList : StateFlow<MutableList<String>>
    val exerciseReceiverList : StateFlow<MutableList<String>>
    val leisureReceiverList : StateFlow<MutableList<String>>
    val cultureList :List<String>
    val exerciseList :List<String>
    val leisureList :List<String>
    fun fetch(list: List<String>)
    fun setCultureReceiver(input: String)
    fun setExerciseReceiver(input: String)
    fun setLeisureReceiver(input: String)
}