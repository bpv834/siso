package com.likelion.network.util


import android.content.Context
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import timber.log.Timber

/**
 * Agora 음성 통화 관리를 위한 클래스입니다.
 * Agora App ID를 생성자에서 주입받도록 하여 유연성을 높였습니다.
 *
 * @param context 애플리케이션 컨텍스트
 * @param appId Agora 애플리케이션 ID (생성자에서 주입받음)
 */
class AgoraVoiceManager(private val context: Context, private val appId: String) { // appId를 생성자 매개변수로 추가

    // Agora RtcEngine 인스턴스
    private var rtcEngine: RtcEngine? = null

    // Agora SDK 이벤트 핸들러
    private val eventHandler = object : IRtcEngineEventHandler() {
        /**
         * 채널 참여 성공 콜백
         * @param channel 참여한 채널 이름
         * @param uid 로컬 사용자 ID
         * @param elapsed 채널 참여까지 걸린 시간 (밀리초)
         */
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Timber.d("채널 참여 성공: $channel, uid: $uid")
        }

        /**
         * 원격 사용자가 채널에 참여했을 때 콜백
         * @param uid 참여한 원격 사용자 ID
         * @param elapsed 사용자가 채널에 참여하는 데 걸린 시간 (밀리초)
         */
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Timber.d("사용자 참여: $uid")
        }

        /**
         * 원격 사용자가 채널에서 나갔을 때 콜백
         * @param uid 나간 원격 사용자 ID
         * @param reason 사용자가 나간 이유 (예: CONNECTION_INTERRUPTED, QUIT)
         */
        override fun onUserOffline(uid: Int, reason: Int) {
            Timber.d("사용자 나감: $uid, reason: $reason")
        }
    }

    init {
        // Rtc 엔진을 초기화하는 시점에 주목하자
        // 문서엔 activity에서 시작점에 하지만
        // 우리는 힐트로 주입받아 처음 이 싱글톤 객체를 사용할때 한다.
        // 그 이후 재사용할때 init은 돌아가지 않아 rtcEngine도 생성되지 않는다
        // 공식문서에서 앱에선 한개의 엔진만 사용하고 여러개 사용할 경우 문제가 발생한다고 한다.

        // RtcEngine 인스턴스를 생성합니다.
        // 이 메서드는 앱 생명주기 동안 한 번만 호출하는 것이 권장됩니다.
        // eventHandler는 config와 함께 create 메서드의 인자로 전달됩니다.
        rtcEngine = RtcEngine.create(context,appId, eventHandler) // eventHandler를 create 메서드의 두 번째 인자로 전달
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
        rtcEngine?.joinChannel(token, channelName, uid, options)
        Timber.d("채널 참여 시도: $channelName, uid: $uid")
    }

    /**
     * 현재 참여 중인 Agora 채널에서 나갑니다.
     */
    fun leaveChannel() {
        rtcEngine?.leaveChannel()
        Timber.d("채널에서 나감")
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
        Timber.d("RtcEngine 리소스 해제")
    }
}
