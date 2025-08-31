package com.likelion.home.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.mypage.usecase.UsersFullUseCase
import com.likelion.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel@Inject constructor(
    val usersFullUseCase: UsersFullUseCase
): ViewModel(),MyPageViewModelType{
    private val _uiState = MutableStateFlow(MyPageUiState())
    override val uiState : StateFlow<MyPageUiState> = _uiState.asStateFlow()
    fun getUsers(id:Long){
        viewModelScope.launch {
            val usersFullModel = usersFullUseCase(id)
            _uiState.update { it.copy(
                userImages = usersFullModel.userImages,
                nickname = usersFullModel.nickname,
                age = usersFullModel.age.toString(),
                location = usersFullModel.location,
                progressValue =  if (usersFullModel.userImages.isNotBlank()) { // 0.02
                    0.02F
                } else 0F
                        +if (usersFullModel.nickname.isNotBlank()) {
                    0.02F
                } else 0F
                        +if (usersFullModel.age > 0) {
                    0.02F
                } else 0F
                        +if (usersFullModel.voiceUrl.isNotBlank())
                    0.02F
                else 0F
                        +if (usersFullModel.introduce.isNotBlank())
                    0.02F
                else 0F
                        +if (usersFullModel.sex.isNotBlank())// 0.1
                    0.1F
                else 0F
                        +if (usersFullModel.preferenceSex.isNotBlank())
                    0.1F
                else 0F
                        +if (usersFullModel.location.isNotBlank())
                    0.1F
                else 0F
                        +if (usersFullModel.religion.isNotBlank())// 0.075
                    0.075F
                else 0F
                        +if (usersFullModel.isSmoke.isNotBlank())
                    0.075F
                else 0F
                        +if (usersFullModel.drinkingCapacity.isNotBlank())
                    0.075F
                else 0F
                        +if (usersFullModel.mbti.isNotBlank())// 0.075
                    0.075F
                else 0F
                        +if (usersFullModel.interests.isNotEmpty())// 0.15
                    0.15F
                else 0F
                        +if (usersFullModel.meeting.isNotEmpty())// 0.15
                    0.15F
                else 0F,
            ) }
        }

    }
}
