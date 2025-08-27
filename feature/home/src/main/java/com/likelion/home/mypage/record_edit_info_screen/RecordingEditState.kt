package com.likelion.home.mypage.record_edit_info_screen

enum class RecordingEditState {
    IDLE,       // 녹음 전 (준비 상태)
    RECORDING,  // 녹음 중
    FINISHED,   // 녹음 완료
    RE_EDIT     // 다시 녹음
}