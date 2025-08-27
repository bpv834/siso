package com.likelion.data.login.mapper

import com.likelion.data.enum_pack.Call
import com.likelion.data.enum_pack.DrinkingCapacity
import com.likelion.data.enum_pack.Location
import com.likelion.data.enum_pack.PreferenceContact
import com.likelion.data.enum_pack.Religion
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.remote.model.request.UserProfileRequest
import timber.log.Timber


// UserSignUpProfile을 UserProfileRequest로 변환하는 확장 함수
fun UserSignUpProfile.toUserProfileRequest(): UserProfileRequest {
    val result =UserProfileRequest(
        drinkingCapacity = DrinkingCapacity.FREQUENTLY.name, // TODO: 실제 enum 값 또는 매핑 로직 필요
        religion = Religion.CHRISTIANITY.name, // TODO: 실제 enum 값 또는 매핑 로직 필요
        smoke = false, // TODO: 실제 값 매핑 필요
        age = this.age,
        nickname = this.nickname,
        introduce = this.introduce,
        preferenceContact = PreferenceContact.CALL.name, // TODO: 실제 enum 값 또는 매핑 로직 필요
        location = Location.GYEONGGI.name, // TODO: 실제 enum 값 또는 매핑 로직 필요
        sex = this.gender, // 클라이언트의 gender를 서버의 sex로 매핑
        preferenceSex = this.preferenceSex
    )
    Timber.d("result = $result")
    return result
}

// TODO: UserProfileResponse를 다시 클라이언트의 UserProfile 모델로 변환하는 매퍼도 필요할 수 있습니다.
// fun UserProfileResponse.toUserSignUpProfile(): UserSignUpProfile { ... }
