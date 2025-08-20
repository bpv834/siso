package com.likelion.remote.api

import com.likelion.remote.model.request.StartCallRequest
import com.likelion.remote.model.response.CallInfoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface CallApiService {

    // 예시: 특정 사용자 간의 통화 시작을 요청하고, 채널 정보를 받아오는 API
    // 실제 백엔드 구현에 따라 @GET 또는 @POST를 사용할 수 있습니다.
    // 여기서는 통화 시작 요청을 Body로 보내므로 @POST를 사용합니다.
    @POST("call/AtoB") // 가정된 API 엔드포인트
    suspend fun requestCallSession(
        @Body request: StartCallRequest // 통화 요청에 필요한 정보 (예: 발신자, 수신자 ID)
    ): CallInfoDto

    // 다른 통화 관련 API (예: 현재 활성 채널 목록 가져오기 등)가 있다면 여기에 추가할 수 있습니다.
    // @GET("call/activeChannels")
    // suspend fun getActiveChannels(): List<ChannelDto>
}