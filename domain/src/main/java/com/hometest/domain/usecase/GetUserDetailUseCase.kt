package com.hometest.domain.usecase

import com.hometest.domain.model.UserDetail
import com.hometest.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetUserDetailUseCase(
    private val userRepository: UserRepository,
    coroutineDispatcher: CoroutineDispatcher
): UseCase<String, UserDetail>(coroutineDispatcher) {

    override suspend fun execute(parameters: String): UserDetail {
        return userRepository.getUserDetails(parameters)
    }
}