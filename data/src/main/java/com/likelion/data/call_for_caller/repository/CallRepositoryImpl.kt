package com.likelion.data.call_for_caller.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.likelion.data.call_for_caller.mapper.toDomain
import com.likelion.data.call_for_caller.mapper.toRemote
import com.likelion.data.home.mapper.toDomainModel
import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.model.CallInfoModel
import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.model.CallRejectResponseModel
import com.likelion.domain.call_for_caller.repository.CallRepository
import com.likelion.network.util.AgoraVoiceManager
import com.likelion.remote.api.CallApiService
import com.likelion.remote.model.request.CallRequest
import com.likelion.remote.model.response.CallInfoDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class CallRepositoryImpl @Inject constructor(
    private val agoraVoiceManager: AgoraVoiceManager, // Agora SDK와 직접 상호작용
    private val callApiService: CallApiService // 백엔드로부터 통화 정보(토큰, 채널명)를 가져오는 역할
) : CallRepository {

    // 내부에서 이벤트를 발행하기 위한 MutableSharedFlow
    private val _agoraEvents = MutableSharedFlow<AgoraEvent>()

    // 인터페이스에서 노출하는 SharedFlow (읽기 전용)
    override val agoraEvents: SharedFlow<AgoraEvent> = _agoraEvents.asSharedFlow()

    // Repository 내부에서 코루틴을 관리할 스코프 (예: Application 스코프에 연결)
    // 실제 앱에서는 DI를 통해 적절한 생명주기를 가진 CoroutineScope를 주입받는 것이 좋습니다.
    // 여기서는 예시를 위해 SupervisorJob과 IO 디스패처를 사용합니다.
    private val repositoryScope = CoroutineScope(SupervisorJob())

    init {

        // AgoraVoiceManager로부터 이벤트를 수집하여 _agoraEvents로 전달
        repositoryScope.launch {
            // agoraVoiceManager.agoraEvents.collect { event -> ... }
            //  SharedFlow의 핵심적인 '수집(collect)' 또는 '구독(subscribe)' 연산입니다.
            // 호출되면 SharedFlow가 종료되거나, 자신이 속한 코루틴이 취소될 때까지 계속해서 새로운 이벤트가 방출(emit)되기를 기다립니다.
            agoraVoiceManager.agoraEvents.collect { event ->
                _agoraEvents.emit(event)
            }
        }
    }

    /**
     * 통화를 시작하고, 서버로부터 통화 정보를 받아 Agora 채널에 참여합니다.
     * 이 함수는 suspend 키워드가 붙어 있어 코루틴 내에서 비동기적으로 실행됩니다.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun startCall(receiverId: Long, accessToken: String): Result<CallInfoModel> {
        Timber.d("CallRepositoryImpl: 통화 시작 요청. CallApiService를 통해 서버 통화 정보 요청 중...")

        val request = CallRequest(receiverId = receiverId)

        return try {
            // 채널명 토큰을 서버에서 불러옴
            val response: Response<CallInfoDto> =
                callApiService.requestCallSession(authorization = accessToken, request = request)
            // 통신이 성공했다면
            if (response.isSuccessful) {
                val callInfoDto = response.body() ?: throw Exception("서버 응답 본문이 비어있습니다.")

                // ⭐️ 매퍼를 사용하여 Remote 모델을 Domain 모델로 변환
                val callInfoModel = callInfoDto.toDomainModel()

                // 📡 서버 응답 성공 → 전화 시도 중 상태 이벤트 발행
                _agoraEvents.emit(AgoraEvent.CallerJoinedChannel)

                agoraVoiceManager.joinChannel(
                    token = callInfoModel.token,
                    channelName = callInfoModel.channelName
                )
                Result.success(callInfoModel)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "서버 에러: ${response.code()} - ${errorBody ?: "내용 없음"}"
                Timber.e(errorMessage)
                // 통화 시작 실패 시 에러 이벤트 발행
                _agoraEvents.emit(AgoraEvent.CallError(response.code(), errorMessage))
                Result.failure(Exception(errorMessage))
            }
        } catch (e: HttpException) {
            Timber.e(e, "HTTP 에러 발생: ${e.message}")
            _agoraEvents.emit(AgoraEvent.CallError(-1, "HTTP 에러: ${e.message}"))
            Result.failure(e)
        } catch (e: IOException) {
            Timber.e(e, "네트워크 연결 에러 발생")
            _agoraEvents.emit(AgoraEvent.CallError(-2, "네트워크 에러: ${e.message}"))
            Result.failure(e)
        } catch (e: Exception) {
            Timber.e(e, "알 수 없는 에러 발생")
            _agoraEvents.emit(AgoraEvent.CallError(-3, "알 수 없는 에러: ${e.message}"))
            Result.failure(e)
        }
    }

    /**
     * 통화를 종료하고 Agora 리소스를 해제합니다.
     */
    override suspend fun endCall() {
        // AgoraVoiceManager를 사용하여 채널에서 나갑니다.
        agoraVoiceManager.leaveChannel()
        agoraVoiceManager.destroy() // RtcEngine 리소스 해제
        Timber.d("CallRepositoryImpl: 통화 종료 및 Agora 리소스 해제 완료")
        // 통화 종료 이벤트는 AgoraVoiceManager에서 'CallerLeftChannel' 등으로 발행될 것입니다.
    }

    // 상대방이 거절할때 발생하는 usecase
    override suspend fun rejectCall(): Result<Unit> {
        return try {
            Timber.d("CallRepositoryImpl: 상대방이 통화를 거절했습니다. 채널 종료 처리 중...")

            // Agora 채널 나가기
            agoraVoiceManager.leaveChannel()
            agoraVoiceManager.destroy()

            // 이벤트 발행 (UI가 거절 화면 표시 가능)
            _agoraEvents.emit(AgoraEvent.CallRejected)

            // 성공 리턴
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "통화 거절 처리 중 에러 발생")
            Result.failure(e)
        }
    }

    override suspend fun denyCall(
        accessToken: String,
        request: CallModel
    ): Result<CallRejectResponseModel> {
        return try {
            // 도메인 모델 → Remote DTO로 변환
            val remoteRequest = request.toRemote()

            // API 호출
            val response = callApiService.denyCall(
                authorization = "Bearer $accessToken",
                request = remoteRequest
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}