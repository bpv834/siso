package com.likelion.remote.fake_api

import com.likelion.remote.api.CallApiService
import com.likelion.remote.model.request.StartCallRequest
import com.likelion.remote.model.response.CallInfoDto
import kotlinx.coroutines.delay
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * CallApiService 인터페이스의 가짜(Fake) 구현체입니다.
 * 실제 백엔드 서버가 준비되지 않았을 때 개발 및 테스트 목적으로 사용됩니다.
 */
class FakeCallApiService @Inject constructor() : CallApiService {

/*    // 실제 API 호출 대신 가짜 데이터를 반환합니다.
    // 실제 백엔드 서버와 연동되는 CallApiService의 구현체에서는 이 request: StartCallRequest 인자가 매우 중요하게 사용됩니다.
    override suspend fun requestCallSession(request: StartCallRequest): Result<CallInfoModel> {
        Timber.Forest.d("FakeCallApiService: 가상 서버에 통화 정보 요청 중...")
        delay(1000L) // 실제 네트워크 지연을 시뮬레이션하기 위한 딜레이

        // 여기에서 가짜 채널명, 토큰, UID를 반환합니다.
        // 필요에 따라 이 값을 변경하여 다양한 시나리오를 테스트할 수 있습니다.
        val fakeChannelName = "test"
        val fakeToken = "007eJxTYJhtn+3M4B/YL3ZS0ZhT4dm7e6JfNvmd37Lk0/ywWwX3g7IVGBItDU1SUxItzI2TDUzSDCwTzY1SU82TUoxTTY2MjA0MzjUuzWgIZGRgyPjLwAiFID4LQ0lqcQkDAwBDPh/m"
        val fakeUid = 0 // 랜덤 UID 생성

        Timber.Forest.d("FakeCallApiService: 가상 통화 정보 수신 완료 - 채널명: $fakeChannelName, 토큰: $fakeToken, UID: $fakeUid")
        return CallInfoDto(
            channelName = fakeChannelName,
            token = fakeToken,
            uid = fakeUid
        )
    }*/

    // 다른 API 메서드가 있다면 여기에 가짜 구현을 추가합니다.
    // override suspend fun getActiveChannels(): List<ChannelDto> {
    //     delay(500L)
    //     return emptyList()
    // }
    override suspend fun requestCallSession(request: StartCallRequest): Response<CallInfoDto> {
        TODO("Not yet implemented")
    }
}