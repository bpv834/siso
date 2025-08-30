package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import com.likelion.home.navigation.Pub
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAdditionalInfoReligionScreenViewModel(
    // usecase자리
): AdditionalInfoReligionScreenViewModelType {
    private var _receiver = MutableStateFlow("")

    override val receiver = _receiver.asStateFlow()
    override val religionList = listOf(
        "기독교",
        "불교",
        "가톨릭",
        "무교",
        "기타"
    )
    override fun fetch(religion : String){
        if (religion.isNotBlank() && religionList.contains(religion)){
            _receiver.update { religion }
        }
    }

    override fun complete(religion : String,nav:(String)->Unit){
        if (religion.isNotBlank()){
            _receiver.update { religion }
            nav(religion)
        }
    }

}