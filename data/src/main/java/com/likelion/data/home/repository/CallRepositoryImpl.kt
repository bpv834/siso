package com.likelion.data.home.repository

import com.likelion.domain.home.repository.CallRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

class CallRepositoryImpl@Inject constructor(
    //mapper
)  : CallRepository {
    override fun call(): String {
        TODO("Not yet implemented")
    }

    override fun endCall() {
        TODO("Not yet implemented")
    }
}