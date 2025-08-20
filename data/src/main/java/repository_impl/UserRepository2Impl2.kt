package repository_impl

import api.test.FakeImagesApi
import api.test.FakeInterestApi
import api.test.FakeProfileApi
import api.test.FakeUserApi
import api.test.FakeVoiceApi
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.repository.UsersRepository2

class UserRepository2Impl2(
    private val userApi: FakeUserApi,
    private val profileApi: FakeProfileApi,
    private val imagesApi: FakeImagesApi,
    private val voiceApi: FakeVoiceApi, // 🎤 음성 API 추가
    private val interestApi: FakeInterestApi // ❤️ 관심사 API 추가
) : UsersRepository2 {

    override suspend fun getAllUsers(): List<UsersModel> {
        val userEntities = userApi.getUsersEntity()
        val profileEntities = profileApi.getProfileEntity()
        val imagesEntities = imagesApi.getImagesEntity()
        val voiceEntities = voiceApi.getVoiceSamplesEntity()
        val interestsEntities = interestApi.getInterestsEntity()

        // 🆔 사용자 ID를 키로 데이터를 매칭
        val profileMap = profileEntities.associateBy { it.userId }
        val imagesMap = imagesEntities.groupBy { it.userId }
        val voiceMap = voiceEntities.associateBy { it.userId }
        val interestsMap = interestsEntities.groupBy { it.userId }

        return userEntities.mapNotNull { userEntity ->
            val profile = profileMap[userEntity.id]
            val images = imagesMap[userEntity.id]?.map { it.path ?: "" } ?: emptyList()
            val voice = voiceMap[userEntity.id]
            val interests = interestsMap[userEntity.id]?.map { it.interest } ?: emptyList()

            if (profile != null) {
                UsersModel(
                    id = userEntity.id,
                    isOnline = userEntity.isOnline,
                    userImages = images,
                    location = profile.location,
                    nickname = profile.nickname,
                    age = profile.age,
                    voiceUrl = voice?.url ?: "", // 🎤 음성 URL 추가
                    interests = interests, // ❤️ 관심사 목록 추가
                    introduce = profile.introduce ?: ""
                )
            } else {
                // 프로필 정보가 없는 유저는 제외
                null
            }
        }
    }

    override suspend fun getUserById(id: Long): UsersModel {
        val fakeUser = UsersModel(
            id = 4L,
            isOnline = true,
            userImages = listOf(
                "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
            ),
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("풋볼", "영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다."
        )
        return fakeUser
    }
}