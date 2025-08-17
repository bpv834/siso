package com.likelion.main.main_page

import com.likelion.domain.model.UsersModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface MainScreenViewModelType {
    val userList: StateFlow<List<UsersModel>>
    fun getUserList()
}