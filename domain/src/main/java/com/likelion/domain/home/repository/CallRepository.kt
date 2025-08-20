package com.likelion.domain.home.repository

interface CallRepository {
    suspend fun startCall(callerId : Long,receiverId:Long): String
    suspend fun endCall()

}