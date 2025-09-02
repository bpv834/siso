package com.likelion.data.login.mapper

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.remote.model.request.UserProfileRequest
import com.likelion.util.DrinkingCapacity
import com.likelion.util.Location
import com.likelion.util.Mbti
import com.likelion.util.PreferenceSex
import com.likelion.util.Religion
import com.likelion.util.Sex
import timber.log.Timber

// UserSignUpProfile을 UserProfileRequest로 변환하는 확장 함수
fun UserSignUpProfile.toUserProfileRequest(): UserProfileRequest {
    val result =UserProfileRequest(
        age = this.age,
        nickname = this.nickname,//this.nickname,
        introduce = this.introduce,
        sex = Sex.MALE.name, // 클라이언트의 gender를 서버의 sex로 매핑
        preferenceSex = PreferenceSex.OTHER.name,// this.preferenceSex,
        mbti = Mbti.ENFJ.name,
        smoke = true,
        location = Location.GYEONGGI.name,
        religion = Religion.CHRISTIANITY.name,
        drinkingCapacity = DrinkingCapacity.OCCASIONALLY.name,

    )
    Timber.d("result = $result")
    return result
}

// TODO: UserProfileResponse를 다시 클라이언트의 UserProfile 모델로 변환하는 매퍼도 필요할 수 있습니다.
// fun UserProfileResponse.toUserSignUpProfile(): UserSignUpProfile { ... }
