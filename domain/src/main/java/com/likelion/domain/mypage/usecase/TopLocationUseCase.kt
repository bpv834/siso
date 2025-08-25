package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location

interface TopLocationUseCase {
    fun invoke(): Location

}