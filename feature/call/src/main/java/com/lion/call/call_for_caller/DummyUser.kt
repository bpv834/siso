package com.lion.call.call_for_caller

import com.likelion.domain.call_for_caller.model.UsersModel

class DummyUser {
    val fakeUser = UsersModel(
        id = 2L,
        userImages =
            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
        ,
        location = "America",
        nickname = "여덟글자닉네임자",
        age = 65,
        interests = listOf(
            "풋볼",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
        ),

    )

    val fakeOtherUser = UsersModel(
        id = 4L,
        userImages =
            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
      ,
        location = "America",
        nickname = "여덟글자닉네아더",
        age = 65,
        interests = listOf(
            "풋볼",
            "영222화",
            "영222화",

        ),

    )
}