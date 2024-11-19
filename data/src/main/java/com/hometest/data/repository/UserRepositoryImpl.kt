package com.hometest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hometest.data.mapper.toUserDetail
import com.hometest.data.mapper.toUserInfo
import com.hometest.domain.model.UserDetail
import com.hometest.domain.model.UserInfo
import com.hometest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userRemoteMediator: UserRemoteMediator
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(since: Long, perPage: Int): Flow<PagingData<UserInfo>> = Pager(
        config = PagingConfig(pageSize = perPage),
        remoteMediator = userRemoteMediator,
        pagingSourceFactory = { userDataSource.getLocalUsers(since, perPage) }
    ).flow.map { pagingData ->
        pagingData.map { it.toUserInfo() }
    }

    override suspend fun getUserDetails(userName: String): UserDetail {
        val result = userDataSource.getUserDetails(userName)
        if (result.isSuccess) {
            return result.getOrThrow().toUserDetail()
        } else {
            throw result.exceptionOrNull() ?: Exception("Unknown error")
        }
    }
}