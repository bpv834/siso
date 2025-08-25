package com.likelion.data.mypage.model

data class LocationEntity(
    val locationList: List<LocationListEntity>
)

data class LocationListEntity(
    val bottomName: List<String>,
    val topName: String
)