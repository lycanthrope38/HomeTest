package com.hometest.di

import com.hometest.domain.repository.UserRepository
import com.hometest.domain.usecase.GetUserDetailUseCase
import com.hometest.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGetUsersUseCase(
        userRepository: UserRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetUsersUseCase {
        return GetUsersUseCase(userRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideGetUserDetailUseCase(
        userRepository: UserRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetUserDetailUseCase {
        return GetUserDetailUseCase(userRepository, dispatcher)
    }

}