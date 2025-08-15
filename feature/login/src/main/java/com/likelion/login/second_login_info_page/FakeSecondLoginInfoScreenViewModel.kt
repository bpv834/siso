package com.likelion.login.second_login_info_page

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeSecondLoginInfoScreenViewModel : SecondLoginInfoScreenViewModelType {

    // 상태 변수
    private val _selectedInterests = MutableStateFlow<Set<String>>(emptySet())

    // StateFlow를 읽기 변수
    override val selectedInterests: StateFlow<Set<String>> = _selectedInterests.asStateFlow()

    // 다음 버튼 누를 수 있는지 관리하는 상태변수 // 3이상이면 트루
    private val _isPossibleNextState = MutableStateFlow<Boolean>(false)
    override val isPossibleNextState: StateFlow<Boolean> = _isPossibleNextState.asStateFlow()


    // 토글 로직 메서드
    override fun onClickToggle(interest: String) {
        val currentInterests = _selectedInterests.value.toMutableSet()
        if (currentInterests.contains(interest)) {
            currentInterests.remove(interest)
        } else {
            currentInterests.add(interest)
        }
        _selectedInterests.value = currentInterests.toSet()
        _isPossibleNextState.value =if(_selectedInterests.value.size>=3)  true else false
    }

    // 다음 버튼 클릭 메서드
    override fun onClickNextButton() {

    }
}