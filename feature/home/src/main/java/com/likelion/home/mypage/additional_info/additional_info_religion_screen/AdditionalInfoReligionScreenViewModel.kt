package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import androidx.lifecycle.ViewModel
import com.likelion.home.navigation.Pub
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AdditionalInfoReligionScreenViewModel@Inject constructor (
    //usecase자리
): ViewModel(), AdditionalInfoReligionScreenViewModelType {
    private var _receiver = MutableStateFlow("")
    init {
        // 초기 리스트 불러오는 부분
        _receiver.update {
            ""
        }
    }
    override val receiver = _receiver.asStateFlow()

    override fun updateReceiver(religion : String,nav:(String)->Unit){
        if (religion.isNotBlank()){
            nav(religion)
        }
    }

}