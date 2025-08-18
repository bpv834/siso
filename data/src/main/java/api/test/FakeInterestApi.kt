package api.test

import com.likelion.data.model.InterestsEntity


// data/api/FakeInterestApi.kt
class FakeInterestApi {
    fun getInterestsEntity(): List<InterestsEntity> {
        return listOf(
            InterestsEntity(id = "1", userId = 1L, interest = "영화"),
            InterestsEntity(id = "2", userId = 1L, interest = "음악"),
            InterestsEntity(id = "3", userId = 2L, interest = "운동")
        )
    }
}