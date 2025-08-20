package com.likelion.data.home.repository

import com.likelion.domain.home.repository.CallRepository
import com.likelion.network.util.AgoraVoiceManager
import com.likelion.remote.api.CallApiService
import com.likelion.remote.model.request.StartCallRequest
import timber.log.Timber
import javax.inject.Inject


class CallRepositoryImpl @Inject constructor(
    private val agoraVoiceManager: AgoraVoiceManager, // Agora SDK와 직접 상호작용
    private val callApiService: CallApiService // 백엔드로부터 통화 정보(토큰, 채널명)를 가져오는 역할
) : CallRepository {

    /**
     * 통화를 시작하고, 서버로부터 통화 정보를 받아 Agora 채널에 참여합니다.
     * 이 함수는 suspend 키워드가 붙어 있어 코루틴 내에서 비동기적으로 실행됩니다.
     */
    override suspend fun startCall(callerId : Long, receiverId:Long): String {
        Timber.d("CallRepositoryImpl: 통화 시작 요청. CallApiService를 통해 서버 통화 정보 요청 중...")

        // 실제 서버에 통화 시작 요청을 보내고 채널 정보를 받아옵니다.
        // 여기서는 예시로 발신자/수신자 ID를 포함하는 요청을 보낸다고 가정합니다.
        // TODO: 실제 사용자 ID를 여기에 입력하거나, 유스케이스/뷰모델을 통해 전달받아야 합니다.
        val request = StartCallRequest(callerId = callerId, receiverId = receiverId)
        val callInfoDto = callApiService.requestCallSession(request)

        val channelName = callInfoDto.channelName
        val token = callInfoDto.token
        val uid = callInfoDto.uid // 서버에서 할당된 UID 사용 (0이면 Agora가 자동 할당)

        Timber.d("CallRepositoryImpl: 서버로부터 통화 정보 수신 완료. 채널명=$channelName, 토큰=$token, UID=$uid. Agora 채널 참여 시도...")

        // AgoraVoiceManager를 사용하여 실제 Agora 채널에 참여합니다.
        agoraVoiceManager.joinChannel(token, channelName, uid)
        return "통화 시작 요청 - 채널: $channelName, UID: $uid (서버 데이터 사용)"
    }


    /**
     * 통화를 종료하고 Agora 리소스를 해제합니다.
     */
    override suspend fun endCall() { // 인터페이스에 맞춰 suspend 키워드를 제거했습니다.
        // AgoraVoiceManager를 사용하여 채널에서 나갑니다.
        agoraVoiceManager.leaveChannel()
        agoraVoiceManager.destroy() // RtcEngine 리소스 해제
        Timber.d("CallRepositoryImpl: 통화 종료 및 Agora 리소스 해제 완료")
    }
}