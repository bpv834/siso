package com.likelion.data.enum_pack

enum class Mbti(val code: Int, val value: String) {
    INTJ(0, "INTJ"),
    INTP(1, "INTP"),
    ENTJ(2, "ENTJ"),
    ENTP(3, "ENTP"),
    INFJ(4, "INFJ"),
    INFP(5, "INFP"),
    ENFJ(6, "ENFJ"),
    ENFP(7, "ENFP"),
    ISTJ(8, "ISTJ"),
    ISFJ(9, "ISFJ"),
    ESTJ(10, "ESTJ"),
    ESFJ(11, "ESFJ"),
    ISTP(12, "ISTP"),
    ISFP(13, "ISFP"),
    ESTP(14, "ESTP"),
    ESFP(15, "ESFP");

    companion object {
        fun fromValue(value: String): Mbti? {
            return entries.find { it.value.equals(value, ignoreCase = true) }
        }

        fun fromCode(code: Int): Mbti? {
            return entries.find { it.code == code }
        }

        // 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name
        }
    }
}