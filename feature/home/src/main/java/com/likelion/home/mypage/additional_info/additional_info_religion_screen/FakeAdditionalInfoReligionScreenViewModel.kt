package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import com.likelion.home.navigation.Pub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAdditionalInfoReligionScreenViewModel(
    // usecase자리
): AdditionalInfoReligionScreenViewModelType {
    private var _receiverList = MutableStateFlow(listOf<String>())
    init {
        // 초기 리스트 불러오는 부분
        _receiverList.update {
            listOf<String>()
        }
    }
    override val receiverList = _receiverList.asStateFlow()

    override fun updatePubList(list : List<String>,nav:(List<Pub>)->Unit){
        if (list.size>3 && list.size<7){
            nav(list.map { Pub(it) })
        }else{
            nav(receiverList.value.map { Pub(it) })
        }
    }
}