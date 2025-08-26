package com.likelion.data.mypage.repository

import com.google.gson.Gson
import com.likelion.data.mypage.mapper.LocationMapper
import com.likelion.data.mypage.mapper.toBottomDomain
import com.likelion.data.mypage.mapper.toTopDomain
import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

): LocationRepository {
    private var locationEntity = LocationEntity(listOf())

    override fun setJson(json:String){
        locationEntity =
            Gson().fromJson(json, LocationEntity::class.java)
    }

    override fun getTopLocationList(): Location {
        return locationEntity.toTopDomain()
    }

    override fun getBottomLocationList(topName: String): Location {
        return locationEntity.toBottomDomain(topName)
    }
}