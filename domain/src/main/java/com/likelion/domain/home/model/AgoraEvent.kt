package com.likelion.domain.home.model

/**
 * Agora SDK 이벤트를 나타내는 Sealed Class.
 * ViewModel에서 UI 상태를 업데이트하는 데 사용됩니다.
 */
sealed class AgoraEvent {
    // 통화 연결에 성공했을 때, 발신자(caller)가 채널에 참여한 상태
    object CallerJoinedChannel : AgoraEvent()

    // 수신자(receiver)가 채널에 참여했을 때
    object ReceiverJoinedChannel : AgoraEvent()

    // 수신자(receiver)가 채널에서 나갔을 때
    object ReceiverLeftChannel : AgoraEvent()

    // 발신자(caller)가 채널에서 성공적으로 나갔을 때
    object CallerLeftChannel : AgoraEvent()

    // 통화 중 발생한 에러
    data class CallError(val errorCode: Int, val message: String) : AgoraEvent()
}