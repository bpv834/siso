package com.example.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.likelion.domain.notification.usecase.SaveFcmTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//  * 2. AndroidManifest.xml에 서비스 등록
// * 3. onMessageReceived, onNewToken 등 오버라이드
@AndroidEntryPoint
class FcmService @Inject constructor(
    // 서비스는 인자없는 생성자가 있어야한다.
    // 따라서 인자로 받지말고 내부에서 찾아줘야함
) : FirebaseMessagingService() {

    // 내부에서 usecase 찾기
    @Inject
    lateinit var saveFcmTokenUseCase: SaveFcmTokenUseCase

    // 토큰을 발급받을때 dataStore에 저장
    // 로그인할때 토큰, ID 매핑해서 서버에 post
    override fun onNewToken(token: String) {

        Timber.d("onNewToken $token")
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            /* // 우리 서버에 토큰과 유저 정보를 매핑하도록 정보를 post
             sendFcmTokenUseCase(token = FcmToken(token = token))*/
            saveFcmTokenUseCase(token)

        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // 모든 FCM 메시지가 들어올 때 기본 로그
        Timber.d("===== FCM 메시지 수신 =====")
        Timber.d("데이터: ${remoteMessage.data}") // 수신된 모든 데이터를 한 번에 출력

        val type = remoteMessage.data["type"]
        Timber.d("메시지 타입: $type")

        when (type) {
            "CALL" -> {
                val callerId = remoteMessage.data["callerId"]
                val callerName = remoteMessage.data["callerName"]
                val callerImage = remoteMessage.data["callerImage"]
                val agoraChannel = remoteMessage.data["agoraChannel"]
                val agoraToken = remoteMessage.data["agoraToken"]
                val id = remoteMessage.data["id"]

                Timber.d(">> 전화 알림 수신: CALL")
                Timber.d(" - 발신자 ID: $callerId")
                Timber.d(" - 발신자 이름: $callerName")
                Timber.d(" - 발신자 이미지 URL: $callerImage")
                Timber.d(" - 아고라 채널: $agoraChannel")
                Timber.d(" - 아고라 토큰: $agoraToken (보안상 주의)")
                Timber.d(" - 통화 ID: $id")

                // 기존 로직 유지
                CoroutineScope(Dispatchers.IO).launch {
                    FcmEventBus.send(
                        FcmEvent.Call(
                            callerName = callerName ?: "",
                            callerImage = callerImage ?: "",
                            agoraChannel = agoraChannel ?: "",
                            agoraToken = agoraToken ?: "",
                            id = id.toString(),
                            callerId = callerId.toString()
                        )
                    )
                }
            }

            "message" -> {
                val senderId = remoteMessage.data["senderId"]?.toLongOrNull()
                val messageId = remoteMessage.data["messageId"]

                Timber.d(">> 일반 메시지 수신: message")
                Timber.d(" - 발신자 ID: $senderId")
                Timber.d(" - 메시지 ID: $messageId")

                CoroutineScope(Dispatchers.IO).launch {
                    // FcmEventBus.send(FcmEvent.Message(senderId, messageId))
                }
            }

            "CALL_REJECT" -> {
                Timber.d(">> 전화 거절 알림 수신: CALL_REJECT")

                CoroutineScope(Dispatchers.IO).launch {
                    FcmEventBus.send(FcmEvent.Reject)
                }
            }

            else -> {
                Timber.w(">> 알 수 없는 메시지 타입 수신: $type")
            }
        }
    }

    /**
     * 메시지가 서버에서 삭제된 경우 호출
     * 예: 기기가 오프라인 상태에서 너무 많은 메시지가 쌓였을 때
     */
    override fun onDeletedMessages() {
        // 서버와 동기화 필요
    }

    /**
     * 앱에서 발송한 메시지가 성공적으로 전송되었을 때 호출
     */
    override fun onMessageSent(msgId: String) {}

    /**
     * 앱에서 발송한 메시지가 실패했을 때 호출
     */
    override fun onSendError(msgId: String, exception: Exception) {}
}