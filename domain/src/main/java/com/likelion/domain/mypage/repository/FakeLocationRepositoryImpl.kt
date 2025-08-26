package com.likelion.domain.mypage.repository

import com.google.gson.Gson
import com.likelion.domain.mypage.model.Location
import javax.inject.Inject
import kotlin.jvm.java

class FakeLocationRepositoryImpl @Inject constructor(

): LocationRepository {
    private var locationEntity =
        FakeLocationEntity(listOf())

    override fun setJson(json:String){
        locationEntity =
            Gson().fromJson(json, FakeLocationEntity::class.java)
    }

    override fun getTopLocationList(): Location {
        return locationEntity.toTopDomain()
    }

    override fun getBottomLocationList(topName: String): Location {
        return locationEntity.toBottomDomain(topName)
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
): Location {
    return Location(locationList.filter {
        it.topName == topName
    }.map{
        it.bottomName
    }.first())
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