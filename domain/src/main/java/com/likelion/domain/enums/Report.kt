package com.likelion.domain.enums

enum class Report(val code: Int, val description: String) {
    SPAM(0, "스팸성/글/메시지"),
    INAPPROPRIATE(1, "부적절한 내용(욕설,선정적,폭력적)"),
    HARASSMENT(2, "괴롭힘,혐오 발언"),
    IMPERSONATION(3, "사칭,도용"),
    ILLEGAL_CONTENT(4, "불법 컨텐츠,마약,불법광고"),
    SEXUAL_CONTENT(5, "성적,음란한 콘텐츠"),
    VIOLENCE(6, "폭력적/위험적 콘텐츠"),
    PRIVACY(7, "개인정보 유출"),
    OTHER(8, "기타(직접 입력)");

    companion object {
        private val codeMap = values().associateBy { it.code }

        fun fromCode(code: Int): Report? = codeMap[code]

        // code -> 서버 전송용 Enum name 반환
        fun getDtoValueFromCode(code: Int): String? = fromCode(code)?.name
    }
}