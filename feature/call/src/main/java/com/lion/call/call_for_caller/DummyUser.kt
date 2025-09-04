package com.lion.call.call_for_caller

import com.likelion.domain.call_for_caller.model.UserProfileModel

class DummyUser {
    val fakeUser = UserProfileModel(
        nickname = "닉네임은여덟글자",
        profileImageUrl = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
        age = 26,
        location = "경기 동두천시",
        interests = listOf("취미12222")
    )

    val fakeOtherUser = UserProfileModel(
        nickname = "닉네임은여덟글자",
        profileImageUrl = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
        age = 26,
        location = "경기 동두천시",
        interests = listOf("취미12")
    )
}