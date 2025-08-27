package com.likelion.data.enum_pack

enum class PreferenceContact(val code: Int, val description: String) {
    CALL(0, "전화"),
    MESSAGE(1, "문자");

    companion object {
        fun fromCode(code: Int): PreferenceContact? {
            return entries.find { it.code == code }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "CALL", "MESSAGE"
        }
    }
}