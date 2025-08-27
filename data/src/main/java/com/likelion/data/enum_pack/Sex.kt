package com.likelion.data.enum_pack

enum class Sex(val code: Int, val description: String, val dtoValue: String) {
    MALE(0, "남성", "Male"),
    FEMALE(1, "여성", "Female");

    companion object {
        fun fromCode(code: Int): Sex? {
            return entries.find { it.code == code }
        }

        fun getDtoValueFromCode(selectedCode: Int): String? {
            val sexEnum = fromCode(selectedCode)
            return sexEnum?.dtoValue
        }
    }
}