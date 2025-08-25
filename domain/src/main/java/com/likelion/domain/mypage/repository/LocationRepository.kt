package com.likelion.domain.mypage.repository

import com.likelion.domain.mypage.model.Location

interface LocationRepository {
    fun getTopLocationList(): Location
    fun getBottomLocationList(topName: String): List<Location>
}