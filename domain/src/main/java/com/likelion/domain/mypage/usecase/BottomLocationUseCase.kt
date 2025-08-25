package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location

interface BottomLocationUseCase {
    fun invoke(topName: String): List<Location>
}