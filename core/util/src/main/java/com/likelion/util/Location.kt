package com.likelion.util
enum class Location(val code: Int, val koreanName: String) {
    SEOUL(0, "서울"),
    BUSAN(1, "부산"),
    DAEGU(2, "대구"),
    INCHEON(3, "인천"),
    GWANGJU(4, "광주"),
    DAEJEON(5, "대전"),
    ULSAN(6, "울산"),
    SEJONG(7, "세종"),
    GYEONGGI(8, "경기"),
    GANGWON(9, "강원"),
    CHUNGBUK(10, "충북"),
    CHUNGNAM(11, "충남"),
    JEONBUK(12, "전북"),
    JEONNAM(13, "전남"),
    GYEONGBUK(14, "경북"),
    GYEONGNAM(15, "경남"),
    JEJU(16, "제주");

    companion object {
        fun fromCode(code: Int): Location? {
            return entries.find { it.code == code }
        }

        fun fromKoreanName(koreanName: String): Location? {
            return entries.find { it.koreanName == koreanName }
        }

        // code -> 서버 전송용 value(Enum name) 뽑기
        fun getDtoValueFromCode(selectedCode: Int): String? {
            return fromCode(selectedCode)?.name  // "SEOUL", "BUSAN", ...
        }
    }
}