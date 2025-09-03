package com.likelion.data.home.api.test

import com.likelion.data.model.ImagesEntity

// data/api/FakeImagesApi.kt
// ImagesEntity를 반환하는 Fake API
class FakeImagesApi {
    fun getImagesEntity(): List<ImagesEntity> {
        return listOf(
            ImagesEntity(id = 1L, userId = 1L, path = "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202501/24/a8548b11-d96c-4da4-bb83-577d8bb6061a.jpg", serverImageName = "", originalName = "", createdAt = "", updatedAt = ""),
            ImagesEntity(
                id = 2L,
                userId = 1L,
                path =  "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
                serverImageName = "",
                originalName = "",
                createdAt = "",
                updatedAt = ""
            ),
            ImagesEntity(id = 3L, userId = 2L, path = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg", serverImageName = "", originalName = "", createdAt = "", updatedAt = "")
        )
    }
}