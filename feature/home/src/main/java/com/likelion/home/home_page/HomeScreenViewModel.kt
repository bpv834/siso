package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.model.UsersModel
import com.likelion.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel(), HomeScreenViewModelType {
    val _userList = MutableStateFlow<List<UsersModel>>(emptyList())
    override val userList : StateFlow<List<UsersModel>> = _userList.asStateFlow()

    init {
        getUserList()
    }

   @Override
   override fun getUserList(){
        viewModelScope.launch {
            _userList.value = getAllUsersUseCase.execute()
        }
    }
}