
package com.likelion.data.login.repository.di

/*package com.likelion.data.login.di*/

import com.likelion.data.login.repository.InMemoryUserSignUpRepositoryImpl
import com.likelion.data.login.repository.LoginRepositoryImpl
import com.likelion.data.login.repository.TokenRepositoryImpl
import com.likelion.domain.login.repository.TokenRepository
import com.likelion.domain.login.repository.LoginRepository
import com.likelion.domain.login.repository.UserSignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 구현체 연결부
@Module
@InstallIn(SingletonComponent::class)
abstract class LoginDataModule {
    @Binds
    @Singleton
    abstract fun bindAuthTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindUserSignUpRepository(
        impl : InMemoryUserSignUpRepositoryImpl
    ) :  UserSignUpRepository
}