package com.hometest.domain.usecase

import androidx.paging.PagingData
import com.hometest.core.BaseTest
import com.hometest.domain.model.UserInfo
import com.hometest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUsersUseCaseTest : BaseTest() {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val userRepository: UserRepository = mock()

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(userRepository, unconfinedTestDispatcher)
    }

    @Test
    fun invoke_returnsPagingData() = runUnconfinedTest {
        val users = listOf(
            UserInfo(1, "username", "avatarUrl", "htmlUrl"),
            UserInfo(2, "username", "avatarUrl", "htmlUrl")
        )
        val pagingData = PagingData.from(users)
        val params = GetUsersUseCase.Params(since = 0, perPage = 10)

        whenever(
            userRepository.getUsers(
                since = params.since,
                perPage = params.perPage
            )
        ).thenReturn(flowOf(pagingData))

        val resultFlow: Flow<PagingData<UserInfo>> = getUsersUseCase(params)

        val resultList = resultFlow.toList()
        assertEquals(1, resultList.size)
        assertEquals(pagingData, resultList.first())
    }
}