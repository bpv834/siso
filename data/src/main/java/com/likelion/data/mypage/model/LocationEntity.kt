package com.likelion.data.mypage.model

data class LocationEntity(
    val locationList: List<LocationListEntity>, // List<String>
) {

}

data class LocationListEntity(
    val topName: String, // VARCHAR(255), NOT NULL
    val bottomName: List<String>, // VARCHAR(255), NOT NULL
) {

}