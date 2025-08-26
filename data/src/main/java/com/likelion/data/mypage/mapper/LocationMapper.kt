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
    topName: String
): Location {
    return Location(locationList.filter {
        it.topName == topName
    }.map{
        it.bottomName
    }.first())
}





class LocationMapper {
    // 상위 -> 하위
    fun mapDomainToEntity(domain: String,bottomName: List<String>){
        //

    }
}