package com.likelion.data.mypage.repository

import com.likelion.ui.R
import android.content.Context
import com.google.gson.Gson
import com.likelion.data.mypage.mapper.toBottomDomain
import com.likelion.data.mypage.mapper.toTopDomain
import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    context : Context
): LocationRepository {

    init {
        val inputStream = context.resources.openRawResource(R.raw.korea_regions_ordered)
        val jsonString  = inputStream.bufferedReader().use { it.readText() }
        setJson(jsonString)
    }

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