package com.likelion.network.util


import android.content.Context
import com.likelion.domain.call.model.AgoraEvent
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Agora 음성 통화 관리를 위한 클래스입니다.
 * Agora App ID를 생성자에서 주입받도록 하여 유연성을 높였습니다.
 *
 * @param context 애플리케이션 컨텍스트
 * @param appId Agora 애플리케이션 ID (생성자에서 주입받음)
 */
class AgoraVoiceManager(
    private val context: Context, // 여기 추가
    private val appId: String,
    // 이벤트를 발행할 코루틴 스코프를 외부에서 주입받도록 합니다.
    // 이는 AgoraVoiceManager의 생명주기를 관리하는 데 도움이 됩니다.

    // SupervisorJob은 일반 Job과 달리 자식 코루틴의 실패가 부모 Job에게 전파되지 않도록 합니다.
    // 즉, SupervisorJob을 부모로 가진 자식 코루틴이 실패하더라도,
    // 부모 Job은 취소되지 않으며, 다른 형제(sibling) 코루틴들도 영향을 받지 않고 계속 실행됩니다
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob())
) {
    // Agora RtcEngine 인스턴스
    private var rtcEngine: RtcEngine? = null

    // Agora 이벤트를 외부에 노출하기 위한 MutableSharedFlow
    private val _agoraEvents = MutableSharedFlow<AgoraEvent>()
    // 외부에 노출되는 읽기 전용 SharedFlow
    val agoraEvents: SharedFlow<AgoraEvent> = _agoraEvents.asSharedFlow()

    // Agora SDK 이벤트 핸들러
    private val eventHandler = object : IRtcEngineEventHandler() {
        /**
         * 채널 참여 성공 콜백: 로컬 사용자(여기서는 발신자)가 채널에 성공적으로 참여했을 때 호출됩니다.
         * @param channel 참여한 채널 이름
         * @param uid 로컬 사용자 ID
         * @param elapsed 채널 참여까지 걸린 시간 (밀리초)
         */
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Timber.d("AgoraVoiceManager: onJoinChannelSuccess -> channel: $channel, uid: $uid, elapsed: $elapsed ms")
            coroutineScope.launch {
                _agoraEvents.emit(AgoraEvent.CallerJoinedChannel)
            }
        }

        /**
         * 원격 사용자가 채널에 참여했을 때 콜백: 상대방(수신자)이 채널에 참여했을 때 호출됩니다.
         * @param uid 참여한 원격 사용자 ID
         * @param elapsed 사용자가 채널에 참여하는 데 걸린 시간 (밀리초)
         */
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Timber.d("AgoraVoiceManager: 원격 사용자 참여: $uid")
            // 코루틴 스코프 내에서 이벤트 발행
            coroutineScope.launch {
                _agoraEvents.emit(AgoraEvent.ReceiverJoinedChannel)
            }
        }

        /**
         * 원격 사용자가 채널에서 나갔을 때 콜백: 상대방(수신자)이 채널에서 나갔을 때 호출됩니다.
         * @param uid 나간 원격 사용자 ID
         * @param reason 사용자가 나간 이유 (예: CONNECTION_INTERRUPTED, QUIT)
         */
        override fun onUserOffline(uid: Int, reason: Int) {
            Timber.d("AgoraVoiceManager: 원격 사용자 나감: $uid, reason: $reason")
            // 코루틴 스코프 내에서 이벤트 발행
            coroutineScope.launch {
                _agoraEvents.emit(AgoraEvent.ReceiverLeftChannel)
            }
        }

        /**
         * SDK에서 에러가 발생했을 때 콜백
         * @param err 에러 코드
         */
        override fun onError(err: Int) {
            Timber.e("AgoraVoiceManager: Agora SDK 에러 발생 -> err: $err")
            Timber.e("AgoraVoiceManager: 채널 상태: ${rtcEngine?.let { "Engine exists" } ?: "Engine null"}")
            coroutineScope.launch {
                _agoraEvents.emit(AgoraEvent.CallError(err, "Agora SDK Error: $err"))
            }
        }

        /**
         * 채널에서 나갔을 때 콜백: 로컬 사용자(발신자)가 채널에서 성공적으로 나갔을 때 호출됩니다.
         * @param stats 채널 통계 정보
         */
        override fun onLeaveChannel(stats: RtcStats?) {
            Timber.d("AgoraVoiceManager: 채널에서 나감")
            coroutineScope.launch {
                _agoraEvents.emit(AgoraEvent.CallerLeftChannel)
            }
        }
    }
    /**
     * RtcEngine을 초기화하는 메소드입니다. 이 메소드는 마이크 권한이 허용된 후에 호출되어야 합니다.
     * @return 초기화 성공 여부
     */
    fun initializeEngine(): Boolean {
        if (rtcEngine != null) {
            Timber.d("AgoraVoiceManager: RtcEngine is already initialized.")
            return true
        }
        try {
            val config = RtcEngineConfig().apply {
                mContext = context
                mAppId = appId
                mEventHandler = eventHandler

                // 로그 레벨만 설정
                mLogConfig = RtcEngineConfig.LogConfig().apply {
                    level = Constants.LOG_FILTER_DEBUG
                }
            }

            // RtcEngine 초기화
            rtcEngine = RtcEngine.create(config)

            if (rtcEngine != null) {
                Timber.d("AgoraVoiceManager: RtcEngine 초기화 성공")
            } else {
                Timber.e("AgoraVoiceManager: RtcEngine 초기화 실패 - RtcEngine 객체가 null")
                coroutineScope.launch {
                    _agoraEvents.emit(
                        AgoraEvent.CallError(-999, "RtcEngine 초기화 실패: 객체가 null")
                    )
                }
            }
            Timber.d("AgoraVoiceManager: App ID = $appId")
            Timber.d("AgoraVoiceManager: EventHandler attached? ${eventHandler != null}")

            return rtcEngine != null

        } catch (e: Exception) {
            Timber.e(e, "AgoraVoiceManager: RtcEngine 초기화 중 예외 발생")
            coroutineScope.launch {
                _agoraEvents.emit(
                    AgoraEvent.CallError(-999, "RtcEngine 초기화 실패: ${e.message}")
                )
            }
            return false
        }
    }
    /**
     * Agora 채널에 참여합니다.
     * @param token Agora 인증 토큰 (임시 토큰 또는 토큰 서버에서 발급받은 토큰)
     * @param channelName 참여할 채널 이름
     * @param uid 사용자 ID (0으로 설정하면 Agora가 자동으로 할당)
     */
    fun joinChannel(token: String, channelName: String, uid: Int = 0) {
        // ChannelMediaOptions를 설정하여 채널 미디어 옵션을 정의합니다.
        val options = ChannelMediaOptions().apply {
            // 클라이언트 역할을 브로드캐스터(송출자)로 설정합니다.
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // 채널 프로필을 통신 모드(음성 전용 또는 소규모 그룹 비디오)로 설정합니다.
            channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // 마이크 트랙을 퍼블리시(게시)하도록 설정합니다.
            publishMicrophoneTrack = true
            // 기타 필요한 옵션들을 여기에 추가할 수 있습니다.
        }

        // rtcEngine이 null이 아닌 경우에만 joinChannel 호출
        // 수정된 joinChannel 메서드는 uid와 options를 추가 인자로 받습니다.
        val result = rtcEngine?.joinChannel(token, channelName, uid, options)
        Timber.d("result = $result AgoraVoiceManager: 채널 참여 시도: $channelName, uid: $uid")
    }

    /**
     * 현재 참여 중인 Agora 채널에서 나갑니다.
     */
    fun leaveChannel() {
        rtcEngine?.leaveChannel()
        Timber.d("AgoraVoiceManager: 채널에서 나감 요청")
    }

    /**
     * RtcEngine 리소스를 해제합니다.
     * 애플리케이션 종료 시 반드시 호출하여 리소스를 정리해야 합니다.
     */
    fun destroy() {
        // RtcEngine 인스턴스를 해제합니다.
        // destroy()는 동기적으로 작동하며, 리소스 해제 후 다음 작업을 수행할 수 있습니다.
        RtcEngine.destroy()
        rtcEngine = null // 인스턴스 참조를 null로 설정하여 메모리 누수 방지
        Timber.d("AgoraVoiceManager: RtcEngine 리소스 해제")
        // 이벤트를 더 이상 발행하지 않으므로 스코프를 취소할 필요는 없지만,
        // 필요에 따라 coroutineScope.cancel()을 호출하여 내부 코루틴 작업을 중단할 수 있습니다.
    }

    fun toggleMute(isMuted: Boolean) {
        rtcEngine?.muteLocalAudioStream(isMuted)
        Timber.d("AgoraVoiceManager: 마이크 상태 변경, 음소거 여부: $isMuted")
    }

    fun toggleSpeaker(isSpeakerOn: Boolean) {
        rtcEngine?.setEnableSpeakerphone(isSpeakerOn)
        Timber.d("AgoraVoiceManager: 스피커 상태 변경, 스피커폰 사용 여부: $isSpeakerOn")
    }
}
