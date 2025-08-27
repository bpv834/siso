package com.likelion.data.enum_pack

enum class Matching(val code: Int, val description: String) {
    MATCHED(0, "매칭 성립"),
    CALL_AVAILABLE(1, "통화 가능"),
    CALLED(2, "서로 통화 중"),
    AFTER(3, "채팅으로 이동"),
    ENDED(4, "통화 종료");

    companion object {
        fun fromCode(code: Int): Matching? {
            return entries.find { it.code == code }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "MATCHED", "CALL_AVAILABLE", ...
        }
    }
}