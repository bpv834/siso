package com.lion.call

sealed class CallState {
    object Idle : CallState() // 초기 상태: 아무 일 없음
    object TryConnecting : CallState() // 통화 시도 중: '통화 중...' 표시 (서버 통신 및 Agora 채널 조인 대기)
    object CallActive : CallState() // 통화 연결 성공: 발신자(나)가 채널에 참여 완료
    object CallEnd : CallState()// 통화 종료 상태
}