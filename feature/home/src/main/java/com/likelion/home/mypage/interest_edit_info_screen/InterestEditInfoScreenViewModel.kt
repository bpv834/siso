package com.likelion.home.mypage.interest_edit_info_screen

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InterestEditInfoScreenViewModel @Inject constructor (
    //usecase자리
): ViewModel(), InterestEditInfoScreenViewModelType {

    override val cultureList =
        listOf(
            "#음악감상",
            "#사진촬영",
            "#서예",
            "#글쓰기",
            "#영화감상",
            "#전시관람",
            "#클래식감상",
            "#노래부르기",
            "#댄스"
        )
    private val _cultureReceiverList = MutableStateFlow(mutableListOf<String>())
    override val cultureReceiverList = _cultureReceiverList.asStateFlow()
    override val exerciseList = listOf(
        "#등산",
        "#낚시",
        "#요가",
        "#골프",
        "#자전거",
        "#캠핑",
        "#수영",
        "#바둑",
        "#볼링",
        "#탁구",
        "#꽃꽂이",
        "#드라이브"
    )
    private val _exerciseReceiverList = MutableStateFlow(mutableListOf<String>())
    override val exerciseReceiverList = _exerciseReceiverList.asStateFlow()
    override val leisureList =
        listOf(
            "#독서",
            "#베이킹",
            "#뜨개질",
            "#원예",
            "#여행",
            "#맛집",
            "#명상",
            "#와인",
            "#요리",
            "#탁구",
            "#인테리어",
        )

    private val _leisureReceiverList = MutableStateFlow(mutableListOf<String>())
    override val leisureReceiverList = _leisureReceiverList.asStateFlow()

    override fun fetch(list: List<String>){
        val tempCulture = mutableListOf<String>()
        val tempExercise = mutableListOf<String>()
        val tempLeisure = mutableListOf<String>()
        list.forEach {
            when(it){
                in cultureList -> tempCulture.add(it)
                in exerciseList -> tempExercise.add(it)
                in leisureList -> tempLeisure.add(it)
            }
        }
        _cultureReceiverList.update { old-> old.toMutableList().apply { addAll(tempCulture) } }
        _exerciseReceiverList.update { old-> old.toMutableList().apply { addAll(tempExercise) } }
        _leisureReceiverList.update { old-> old.toMutableList().apply { addAll(tempLeisure) } }
    }

    override fun setLeisureReceiver(input: String){
        if (_leisureReceiverList.value.contains(input)) {
            _leisureReceiverList.update {old->
                old.toMutableList().apply { remove(input) }
            }
        } else {
            _leisureReceiverList.update {old->
                old.toMutableList().apply { add(input) }
            }
        }
    }

    override fun setExerciseReceiver(input: String){
        if (exerciseReceiverList.value.contains(input)) {
            _exerciseReceiverList.update {old->
                old.toMutableList().apply { remove(input) }
            }
        } else {
            _exerciseReceiverList.update {old->
                old.toMutableList().apply { add(input) }
            }
        }
    }

    override fun setCultureReceiver(input: String){
        if (cultureReceiverList.value.contains(input)) {
            _cultureReceiverList.update {old->
                old.toMutableList().apply { remove(input) }
            }
        } else {
            _cultureReceiverList.update {old->
                old.toMutableList().apply { add(input) }
            }
        }
    }
}