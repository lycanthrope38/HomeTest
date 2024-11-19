package com.hometest.data.repository

import androidx.paging.PagingSource
import com.hometest.data.api.UserApi
import com.hometest.data.database.UserDao
import com.hometest.data.database.UserEntity
import com.hometest.data.mapper.toUserEntity
import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse

class UserDataSourceImpl(
    private val api: UserApi,
    private val userDao: UserDao,
) : UserDataSource {
    override suspend fun getRemoteUsers(since: Long, perPage: Int): Result<List<UserResponse>> {
        return trySafe { api.getUsers(since = since, perPage = perPage) }
    }

    override fun getLocalUsers(since: Long, perPage: Int): PagingSource<Int, UserEntity> {
        return userDao.getUsers()
    }

    override suspend fun getUserDetails(userName: String): Result<UserDetailResponse> {
        return trySafe { api.getUserDetails(userName) }
    }

    override suspend fun saveLocalUsers(users: List<UserResponse>) {
        userDao.saveUsers(users.map { it.toUserEntity() })
    }

    override suspend fun clearLocalUsers() {
        userDao.clearUsers()
    }
}

suspend fun <T> trySafe(func: suspend () -> T): Result<T> = try {
    Result.success(func.invoke())
} catch (e: Exception) {
    Result.failure(e)
}