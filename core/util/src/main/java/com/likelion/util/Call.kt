package com.likelion.util

enum class Call(val code: Int, val description: String) {
    REQUESTED(0, "통화 요청"),
    ACCEPTED(1, "승낙"),
    DENIED(2, "거절"),
    ENDED(3, "통화 종료");

    companion object {
        fun fromCode(code: Int): Call? {
            return entries.find { it.code == code }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // ex) "REQUESTED", "ACCEPTED" ...
        }
    }
}