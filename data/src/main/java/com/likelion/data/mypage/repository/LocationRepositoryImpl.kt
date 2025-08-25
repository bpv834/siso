package com.likelion.data.mypage.repository

import com.google.gson.Gson
import com.likelion.data.mypage.mapper.LocationMapper
import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationMapper: LocationMapper
): LocationRepository {
    private val locationList =
        Gson().fromJson("korea_regions_ordered.json", LocationEntity::class.java)

    override fun getTopLocationList(): Location {
        return locationMapper.mapEntityToTopLocation(locationList)
    }

    override fun getBottomLocationList(topName: String): List<Location> {
        return locationMapper.mapEntityToBottomLocation(locationList, topName)
    }
}