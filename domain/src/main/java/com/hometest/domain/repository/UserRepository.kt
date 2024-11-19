package com.hometest.domain.repository

import androidx.paging.PagingData
import com.hometest.domain.model.UserDetail
import com.hometest.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(since: Long, perPage: Int): Flow<PagingData<UserInfo>>
    suspend fun getUserDetails(userName: String): UserDetail
}