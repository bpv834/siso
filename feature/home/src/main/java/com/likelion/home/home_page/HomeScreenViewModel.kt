package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getTokenAllUseCase: GetTokenAllUseCase,
) : ViewModel(), HomeScreenViewModelType {
    val _userList = MutableStateFlow<List<UsersModel>>(emptyList())
    override val userList: StateFlow<List<UsersModel>> = _userList.asStateFlow()


    init {
        getUserList()
    }

    @Override
    override fun getUserList() {
        viewModelScope.launch {
            val user = getTokenAllUseCase.invoke().first()
            val result = getAllUsersUseCase.execute(user?.accessToken?:"")
            if (result.isSuccess) {
                result.map { _userList.value = it }
            }
        }
    }

    // 전화 기능은 caller Screen에서 동작하고 홈에선 id만 전달해주고 call screen 오픈만 담당
    override fun onClickCallButton(callerId: Long, receiverId: Long) {

    }

    /**
     * 통화 실패 상태를 초기화하여 다시 통화를 시도할 수 있도록 합니다.
     * 홈 화면에서 통화 실패 메시지를 닫거나 다시 시도할 때 사용될 수 있습니다.
     */
    override fun resetCallState() {
    }
}