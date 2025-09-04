package com.likelion.remote.api

import com.likelion.remote.model.ApiResponse
import com.likelion.remote.model.response.ChatRoomResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApiService {

    // 메시지 전송
    @POST("/api/chats/messages")
    suspend fun postChat(

    )

    // 채팅방 가져오기
    @GET("/api/chats/rooms")
    suspend fun getChatRoom(
        @Header("Authorization") accessToken: String
    ): Response<ApiResponse<List<ChatRoomResponseDto>>>
}