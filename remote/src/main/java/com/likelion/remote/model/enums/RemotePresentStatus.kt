package com.likelion.remote.model.enums

enum class RemotePresentStatus(val code: Int, val description: String) {
    OFFLINE(0, "오프라인"),
    ONLINE(1, "온라인"),
    IN_CALL(2, "통화중");

    companion object {
        // 서버 코드 -> Enum
        fun fromCode(code: Int?): RemotePresentStatus? {
            return RemotePresentStatus.entries.find { it.code == code }
        }

        // Enum -> 서버 전송용 코드
        fun toCode(status: RemotePresentStatus?): Int? {
            return status?.code
        }
    }
}