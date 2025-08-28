package com.example.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.usecase.SendFcmTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//  * 2. AndroidManifest.xml에 서비스 등록
// * 3. onMessageReceived, onNewToken 등 오버라이드
@AndroidEntryPoint
class FcmService @Inject constructor(
) : FirebaseMessagingService() {

    @Inject
    lateinit var sendFcmTokenUseCase: SendFcmTokenUseCase

    // 서버로 토큰 전송
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            sendFcmTokenUseCase(token = FcmToken(token = token))
        }
    }

    // * 앱이 FCM 메시지를 받을 때 호출됩니다.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val type = remoteMessage.data["type"]

        when (type) {
            "CALL" -> {
                // 전화는 UI 바로 띄우기 위해 모든 정보 포함 가능
                val callerName = remoteMessage.data["callerName"]
                val callerImage = remoteMessage.data["callerImage"]
                val agoraChannel = remoteMessage.data["agoraChannel"]
                val agoraToken = remoteMessage.data["agoraToken"]

                CoroutineScope(Dispatchers.IO).launch {
                    FcmEventBus.send(
                        FcmEvent.Call(
                            callerName = callerName ?: "",
                            callerImage = callerImage ?: "",
                            agoraChannel = agoraChannel ?: "",
                            agoraToken = agoraToken ?: ""
                        )
                    )
                }
            }

            "message" -> {
                val senderId = remoteMessage.data["senderId"]?.toLongOrNull() ?: return
                val messageId = remoteMessage.data["messageId"] ?: return

                // 메시지는 알림 클릭 시 상세 데이터를 서버에서 조회
                CoroutineScope(Dispatchers.IO).launch {
                    //   FcmEventBus.send(FcmEvent.Message(senderId, messageId))
                }
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