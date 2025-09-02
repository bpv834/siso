package com.likelion.remote.api

import com.likelion.remote.model.request.CallRequest
import com.likelion.remote.model.request.RejectCallRequest
import com.likelion.remote.model.response.ApiToken
import com.likelion.remote.model.response.CallInfoDto
import com.likelion.remote.model.response.RejectCallResponse
import com.likelion.remote.model.response.SisoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CallApiService {

    // 예시: 특정 사용자 간의 통화 시작을 요청하고, 채널 정보를 받아오는 API
    // 실제 백엔드 구현에 따라 @GET 또는 @POST를 사용할 수 있습니다.
    // 여기서는 통화 시작 요청을 Body로 보내므로 @POST를 사용합니다.
    @POST("/api/calls/request")
    suspend fun requestCallSession(
        @Header("Authorization") accessToken: String,
        @Body request: CallRequest
    ): SisoResponse<CallInfoDto>

    @POST("/api/calls/accept")
    suspend fun acceptCall(
        @Body callInfo: CallInfoDto
    ): SisoResponse<CallInfoDto>

/*    @POST("/api/calls/deny")
    suspend fun denyCall(
        @Body callInfo: CallInfoDto
    ): SisoResponse<CallInfoDto>

    @POST("/api/calls/end")
    suspend fun endCall(
        @Body callInfo: CallInfoDto,
        @Query("continueRelationship") continueRelationship: Boolean
    ): SisoResponse<CallInfoDto>*/
}