package com.likelion.data.mypage.mapper

import com.likelion.data.mypage.model.LocationEntity
import com.likelion.domain.mypage.model.Location


fun LocationEntity.toTopDomain(): Location{
    // 변환 하위 -> 상위
    return Location(locationList.map {
        it.topName
    })
}

fun LocationEntity.toBottomDomain(
    entity: LocationEntity,
    topName: String
): List<Location>{
    // 변환 하위 -> 상위
    locationList
    val locationList = mutableListOf<Location>()
    entity.locationList.forEach {
        if (it.topName == topName) {
            locationList.add(Location(it.bottomName))
            return@forEach
        }
    }
    return locationList
}


class LocationMapper {
    // 상위 -> 하위
    fun mapDomainToEntity(domain: String,bottomName: List<String>){
        //

    }
}