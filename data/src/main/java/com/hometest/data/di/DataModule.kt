package com.hometest.data.di

import com.hometest.data.api.UserApi
import com.hometest.data.database.UserDao
import com.hometest.data.repository.UserDataSource
import com.hometest.data.repository.UserDataSourceImpl
import com.hometest.data.repository.UserRemoteMediator
import com.hometest.data.repository.UserRepositoryImpl
import com.hometest.domain.repository.UserRepository
import com.hometest.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserDataSource(
        userApi: UserApi,
        userDao: UserDao,
    ): UserDataSource {
        return UserDataSourceImpl(userApi, userDao)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        userDataSource: UserDataSource,
        movieRemoteMediator: UserRemoteMediator,
    ): UserRepository {
        return UserRepositoryImpl(userDataSource, movieRemoteMediator)
    }

    @Provides
    @Singleton
    fun provideMovieMediator(
        movieLocalDataSource: UserDataSource,
    ): UserRemoteMediator {
        return UserRemoteMediator(movieLocalDataSource)
    }
}