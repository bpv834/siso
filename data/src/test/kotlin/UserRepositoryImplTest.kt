import api.test.FakeImagesApi
import api.test.FakeInterestApi
import api.test.FakeProfileApi
import api.test.FakeUserApi
import api.test.FakeVoiceApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import repository_impl.UserRepository2Impl2

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepository2Impl2
    private val testDispatcher = StandardTestDispatcher()

    private val fakeUserApi = FakeUserApi()
    private val fakeProfileApi = FakeProfileApi()
    private val fakeImagesApi = FakeImagesApi()
    private val fakeVoiceApi = FakeVoiceApi()
    private val fakeInterestApi = FakeInterestApi()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepository = UserRepository2Impl2(
            userApi = fakeUserApi,
            profileApi = fakeProfileApi,
            imagesApi = fakeImagesApi,
            voiceApi = fakeVoiceApi,
            interestApi = fakeInterestApi
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllUsers returns correct UsersModel data`() = runTest {
        // Given - 테스트에 필요한 모든 Fake API는 setup에서 주입되었습니다.
        // When - 레포지토리의 getAllUsers() 메서드 호출
        val users = userRepository.getAllUsers()

        // Then - 반환된 데이터가 예상과 일치하는지 검증
        assertEquals(2, users.size)
        // 첫 번째 유저 데이터 검증
        // 첫 번째 유저 데이터 검증
        val user1 = users.find { it.id == 1L }
        // user1이 null이 아님을 명시적으로 확인
        assertTrue(user1 != null)
        // 이후부터는 user1!! 와 같이 non-null asserted call 사용
        assertEquals("코딩러", user1!!.nickname)
        assertTrue(user1.isOnline) // isOnline은 non-nullable이므로 !! 없이 접근 가능
        assertEquals("Seoul", user1.location)
        assertEquals(25, user1.age)
        assertEquals(2, user1.userImages.size)
        assertEquals(listOf("영화", "음악"), user1.interests)
        // 두 번째 유저 데이터 검증
        val user2 = users.find { it.id == 2L }
        assertTrue(user2 != null)
        assertEquals("안드로이드", user2!!.nickname)
        assertEquals(1, user2.userImages.size)
        assertEquals(listOf("운동"), user2.interests)
    }
}