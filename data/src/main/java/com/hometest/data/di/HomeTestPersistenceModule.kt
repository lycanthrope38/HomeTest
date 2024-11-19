package com.hometest.data.di

import android.content.Context
import androidx.room.Room
import com.hometest.data.DATABASE_NAME
import com.hometest.data.database.HomeTestDatabase
import com.hometest.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HomeTestPersistenceModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HomeTestDatabase::class.java, DATABASE_NAME)
            .build()


    @Singleton
    @Provides
    fun provideUserDao(database: HomeTestDatabase): UserDao = database.userDao()
}