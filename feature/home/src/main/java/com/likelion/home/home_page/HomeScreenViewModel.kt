package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.network.util.AgoraVoiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val agoraVoiceManager: AgoraVoiceManager
) : ViewModel(), HomeScreenViewModelType {
    val _userList = MutableStateFlow<List<UsersModel>>(emptyList())
    override val userList : StateFlow<List<UsersModel>> = _userList.asStateFlow()

    init {
        getUserList()
        Timber.d("_users : ${_userList.value}")

    }

   @Override
   override fun getUserList(){
        viewModelScope.launch {
            _userList.value = getAllUsersUseCase.execute()
        }
    }

    override fun onClickCallButton() {
        TODO("Not yet implemented")
    }
}