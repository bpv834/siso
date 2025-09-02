package com.likelion.remote.api

import com.likelion.remote.model.request.CallRequest
import com.likelion.remote.model.request.RejectCallRequest
import com.likelion.remote.model.response.CallInfoDto
import com.likelion.remote.model.response.RejectCallResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CallApiService {

    // 예시: 특정 사용자 간의 통화 시작을 요청하고, 채널 정보를 받아오는 API
    // 실제 백엔드 구현에 따라 @GET 또는 @POST를 사용할 수 있습니다.
    // 여기서는 통화 시작 요청을 Body로 보내므로 @POST를 사용합니다.
    @POST("/api/calls/request") // 가정된 API 엔드포인트
    suspend fun requestCallSession(
        @Header("Authorization") authorization: String,
        @Body request: CallRequest // 통화 요청에 필요한 정보 (예: 발신자, 수신자 ID)
    ): Response<CallInfoDto>

    // 수신 거절
    @POST("/api/calls/deny") // 거절 API 엔드포인트
    suspend fun denyCall(
        @Header("Authorization") authorization: String,
        @Body request: RejectCallRequest // 거절에 필요한 정보
    ): Response<RejectCallResponse>
}