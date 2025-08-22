package com.likelion.home.home_page

import android.util.Log
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FakeHomeScreenViewModel(val getAllUsersUseCase: GetAllUsersUseCase) : HomeScreenViewModelType {
    val _userList = MutableStateFlow(emptyList<UsersModel>())
    override val userList : StateFlow<List<UsersModel>> = _userList.asStateFlow()


    // 새로운 독립적인 Job을 생명주기 관리자로 사용하고, 코루틴은 메인 스레드에서 실행되도록 설정된 CoroutineScope"를 생성하겠다는 의미
    private val fakeViewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    // 코루틴 관리 작업
    private var timerJob: Job? = null

    fun clear() {
        fakeViewModelScope.cancel()
    }

    init {
        getUserList()
    }
    @Override
    override fun getUserList(){
        timerJob?.cancel()
        // 2. 미리 생성한 fakeViewModelScope를 사용해 코루틴을 실행합니다.
        timerJob = fakeViewModelScope.launch {
            _userList.value = getAllUsersUseCase.execute()
            Log.d("test","_userList${_userList.value}")
        }
    }

    override fun onClickCallButton(callerId: Long, receiverId: Long) {
        TODO("Not yet implemented")
    }

    override fun resetCallState() {
        TODO("Not yet implemented")
    }

}