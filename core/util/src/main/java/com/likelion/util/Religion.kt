package com.likelion.util

enum class Religion(val code: Int, val description: String) {
    NONE(0, "종교없음"),
    CHRISTIANITY(1, "기독교"),
    CATHOLIC(2, "천주교"),
    BUDDHISM(3, "불교"),
    ISLAM(4, "이슬람"),
    OTHER(5, "기타");

    companion object {
        fun fromCode(code: Int): Religion? = entries.find { it.code == code }

        // code -> 서버 전송용 value(Enum name) 반환
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "NONE", "CHRISTIANITY", ...
        }
    }
}