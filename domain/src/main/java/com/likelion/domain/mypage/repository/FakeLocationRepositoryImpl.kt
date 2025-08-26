package com.likelion.domain.mypage.repository

import com.google.gson.Gson
import com.likelion.domain.mypage.model.Location
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.collections.map
import kotlin.jvm.java

class FakeLocationRepositoryImpl @Inject constructor(
    json: String
): LocationRepository {

    private var locationList = FakeLocationEntity(listOf())
    init {
        setJson(json)
    }
    override fun setJson(json: String) {
        locationList = Gson().fromJson(json, FakeLocationEntity::class.java)
    }

    override fun getTopLocationList(): Location {
        return locationList.toTopDomain()
    }

    override fun getBottomLocationList(topName: String): Location {
        return locationList.toBottomDomain(topName)
    }
}

fun FakeLocationEntity.toTopDomain(): Location{
    // 변환 하위 -> 상위
    return Location(locationList.map {
        it.topName
    })
}

fun FakeLocationEntity.toBottomDomain(
    topName: String
): Location{
    // 변환 하위 -> 상위
    return Location(
        name = locationList.filter {
            it.topName == topName
        }.map {
            it.bottomName
        }.first()
    )
}

// 상위 -> 하위
fun Location.mapDomainToEntity(domain: String,bottomName: List<String>){
    //

}

data class FakeLocationEntity(
    val locationList: List<FakeLocationListEntity>
)

data class FakeLocationListEntity(
    val bottomName: List<String>,
    val topName: String
)