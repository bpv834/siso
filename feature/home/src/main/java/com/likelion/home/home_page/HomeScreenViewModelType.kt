package com.likelion.home.home_page

import com.likelion.domain.model.UsersModel
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModelType {
    val userList: StateFlow<List<UsersModel>>
    fun getUserList()
}