package com.lion.call.call_for_caller


sealed class CallForCallerState {
    object Idle : CallForCallerState() // 초기 상태: 아무 일 없음
    object Calling : CallForCallerState() // 통화 시도 중: '통화 중...' 표시 (서버 통신 및 Agora 채널 조인 대기)
    object CallActive : CallForCallerState() // 통화 연결 성공: 발신자(나)가 채널에 참여 완료
    data class CallFailed(val message: String) : CallForCallerState() // 통화 실패: 에러 메시지 표시
}