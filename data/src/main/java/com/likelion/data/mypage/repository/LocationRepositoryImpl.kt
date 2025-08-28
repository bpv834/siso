package com.likelion.data.mypage.repository

import android.util.Log.d
import com.google.gson.Gson
import com.likelion.data.mypage.mapper.toBottomDomain
import com.likelion.data.mypage.mapper.toTopDomain
import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.FakeLocationEntity
import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.repository.toBottomDomain
import com.likelion.domain.mypage.repository.toTopDomain
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

): LocationRepository {

    private var locationList = LocationEntity(listOf())

    override fun setJson(json: String) {
        locationList = Gson().fromJson(json, LocationEntity::class.java)
    }

    override fun getTopLocationList(): Location {
        return locationList.toTopDomain()
    }

    override fun getBottomLocationList(topName: String): Location {
        return locationList.toBottomDomain(topName)
    }
}