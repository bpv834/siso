package com.likelion.home.home_page

import com.likelion.domain.home.model.UsersModel
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModelType {
    val userList: StateFlow<List<UsersModel>>
    fun getUserList()
    fun onClickCallButton()
}