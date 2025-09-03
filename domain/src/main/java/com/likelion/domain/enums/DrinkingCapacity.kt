package com.likelion.domain.enums

enum class DrinkingCapacity(val code: Int, val description: String) {
    NEVER(0, "전혀 안함"),
    OCCASIONALLY(1, "가끔 마심(주 1회 ~ 한달에 한번)"),
    FREQUENTLY(2, "자주 마심(주3회이상)");

    companion object {
        fun fromCode(code: Int): DrinkingCapacity? {
            return entries.find { it.code == code }
        }

        fun fromDescription(description: String): DrinkingCapacity? {
            return entries.find { it.description == description }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "NEVER", "OCCASIONALLY", "FREQUENTLY"
        }
    }
}