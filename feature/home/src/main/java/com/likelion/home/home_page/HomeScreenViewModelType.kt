package com.likelion.home.home_page

import com.likelion.domain.home.model.UsersModel
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModelType {
    val userList: StateFlow<List<UsersModel>>
    fun getUserList()
    fun onClickCallButton(callerId : Long =0L ,receiverId:Long =1L)
    fun resetCallState() // 통화 실패 상태를 초기화하는 함수 추가
}