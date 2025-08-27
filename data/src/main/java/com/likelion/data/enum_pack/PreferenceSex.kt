package com.likelion.data.enum_pack

enum class PreferenceSex(val code: Int, val description: String) {
    MALE(0, "남성"),
    FEMALE(1, "여성"),
    OTHER(2, "상관없음");

    companion object {
        fun fromCode(code: Int): PreferenceSex? {
            return entries.find { it.code == code }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "MALE", "FEMALE", "OTHER"
        }
    }
}
