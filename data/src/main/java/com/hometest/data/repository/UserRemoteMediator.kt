package com.hometest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hometest.data.database.UserEntity

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userDataSource: UserDataSource
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val lastUserId = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.lastItemOrNull()?.id ?: 0
        }

        val result = userDataSource.getRemoteUsers(lastUserId, state.config.pageSize)
        if (result.isSuccess) {
            if (loadType == LoadType.REFRESH) {
                userDataSource.clearLocalUsers()
            }

            val users = result.getOrNull() ?: emptyList()
            val endOfPaginationReached = users.size < state.config.pageSize
            userDataSource.saveLocalUsers(users)
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else {
            return MediatorResult.Error(
                result.exceptionOrNull() ?: IllegalStateException("Unknown error")
            )
        }
    }
}