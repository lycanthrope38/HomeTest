package com.hometest.domain.usecase

import androidx.paging.PagingData
import com.hometest.domain.model.UserInfo
import com.hometest.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetUsersUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(parameters: Params): Flow<PagingData<UserInfo>> {
        return userRepository.getUsers(since = parameters.since, perPage = parameters.perPage)
            .flowOn(dispatcher)
    }

    class Params(val since: Long, val perPage: Int)
}