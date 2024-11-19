package com.hometest.data.repository

import androidx.paging.PagingSource
import com.hometest.data.database.UserEntity
import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse

interface UserDataSource {
    suspend fun getRemoteUsers(since: Long, perPage: Int): Result<List<UserResponse>>
    fun getLocalUsers(since: Long, perPage: Int): PagingSource<Int, UserEntity>
    suspend fun getUserDetails(userName: String): Result<UserDetailResponse>
    suspend fun saveLocalUsers(users: List<UserResponse>)
    suspend fun clearLocalUsers()
}