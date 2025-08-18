package api.test

import com.likelion.data.model.ImagesEntity

// data/api/FakeImagesApi.kt
// ImagesEntity를 반환하는 Fake API
class FakeImagesApi {
    fun getImagesEntity(): List<ImagesEntity> {
        return listOf(
            ImagesEntity(id = 1L, userId = 1L, path = "https://example.com/img1_1.jpg", serverImageName = "", originalName = "", createdAt = "", updatedAt = ""),
            ImagesEntity(
                id = 2L,
                userId = 1L,
                path = "https://example.com/img1_2.jpg",
                serverImageName = "",
                originalName = "",
                createdAt = "",
                updatedAt = ""
            ),
            ImagesEntity(id = 3L, userId = 2L, path = "https://example.com/img2_1.jpg", serverImageName = "", originalName = "", createdAt = "", updatedAt = "")
        )
    }
}