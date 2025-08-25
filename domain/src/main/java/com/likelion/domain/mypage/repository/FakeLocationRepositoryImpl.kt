package com.likelion.domain.mypage.repository

import com.google.gson.Gson
import com.likelion.domain.mypage.model.Location
import javax.inject.Inject
import kotlin.jvm.java

class FakeLocationRepositoryImpl @Inject constructor(
    private val locationMapper: FakeLocationMapper
): LocationRepository {
    private val locationList =
        Gson().fromJson("korea_regions_ordered.json", FakeLocationEntity::class.java)

    override fun getTopLocationList(): Location {
        return locationMapper.mapEntityToTopLocation(locationList)
    }

    override fun getBottomLocationList(topName: String): List<Location> {
        return locationMapper.mapEntityToBottomLocation(locationList, topName)
    }
}

class FakeLocationMapper {
    // 하위 -> 상위
    fun mapEntityToTopLocation(entity: FakeLocationEntity): Location {
        return Location(entity.locationList.map {
            it.topName
        })

    }
    fun mapEntityToBottomLocation(
        entity: FakeLocationEntity,
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

data class FakeLocationEntity(
    val locationList: List<FakeLocationListEntity>, // List<String>
) {

}

data class FakeLocationListEntity(
    val topName: String, // VARCHAR(255), NOT NULL
    val bottomName: List<String>, // VARCHAR(255), NOT NULL
) {

}