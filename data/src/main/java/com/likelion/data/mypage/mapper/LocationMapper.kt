package com.likelion.data.mypage.mapper

import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location

class LocationMapper {
    // 하위 -> 상위
    fun mapEntityToTopLocation(entity: LocationEntity): Location {
        return Location(entity.locationList.map {
            it.topName
        })

    }
    fun mapEntityToBottomLocation(
        entity: LocationEntity,
        topName: String
    ): List<Location> {
        val locationList = mutableListOf<Location>()
        entity.locationList.forEach {
            if (it.topName == topName) {
                locationList.add(Location(it.bottomName))
                return@forEach
            }
        }
        return locationList
    }
    // 상위 -> 하위
    fun mapDomainToEntity(domain: String,bottomName: List<String>){
        //

    }
}