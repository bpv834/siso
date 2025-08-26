package com.likelion.domain.mypage.repository

import com.likelion.domain.mypage.model.Location

interface LocationRepository {
    fun setJson(json:String)
    fun getTopLocationList(): Location
    fun getBottomLocationList(topName: String): Location
}