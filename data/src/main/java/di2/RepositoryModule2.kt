package di2

import api.test.FakeImagesApi
import api.test.FakeInterestApi
import api.test.FakeProfileApi
import api.test.FakeUserApi
import api.test.FakeVoiceApi
import com.likelion.domain.repository.UsersRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository_impl.UserRepositoryImpl
import javax.inject.Singleton
import dagger.Module

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule2 {
    @Provides
    @Singleton
    fun provideUsersRepository(
        userApi: FakeUserApi,
        profileApi: FakeProfileApi,
        imagesApi: FakeImagesApi,
        voiceApi: FakeVoiceApi,
        interestApi: FakeInterestApi
    ): UsersRepository { // Domain 계층의 인터페이스를 반환
        return UserRepositoryImpl(userApi, profileApi, imagesApi, voiceApi, interestApi)
    }
}