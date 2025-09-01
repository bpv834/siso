package com.lion.call.call_for_caller

import com.likelion.domain.call_for_caller.model.UsersModel

class DummyUser {
    val fakeUser = UsersModel(
        id = 2L,
        isOnline = true,
        userImages = listOf(
            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
        ),
        location = "America",
        nickname = "여덟글자닉네임자",
        age = 65,
        voiceUrl = "https://example.com/voice1.mp3",
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
        introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다."
    )

    val fakeOtherUser = UsersModel(
        id = 4L,
        isOnline = true,
        userImages = listOf(
            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
        ),
        location = "America",
        nickname = "여덟글자닉네아더",
        age = 65,
        voiceUrl = "https://example.com/voice1.mp3",
        interests = listOf(
            "풋볼",
            "영222화",
            "영222화",

        ),
        introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                " 안녕하세요. 코딩을 좋아하는 개발자입니다."
    )
}