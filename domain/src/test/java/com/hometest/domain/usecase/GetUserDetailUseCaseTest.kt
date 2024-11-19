package com.hometest.domain.usecase

import com.hometest.core.BaseTest
import com.hometest.domain.model.UserDetail
import com.hometest.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserDetailUseCaseTest : BaseTest() {
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    private val userRepository: UserRepository = mock()

    @Before
    fun setUp() {
        getUserDetailUseCase = GetUserDetailUseCase(userRepository, unconfinedTestDispatcher)
    }

    @Test
    fun execute_returnsUserDetail_whenUserExists() = runUnconfinedTest {
        val userDetail = UserDetail(1, "username", "name", "email", "location", 1, 1)
        whenever(userRepository.getUserDetails("username")).thenReturn(userDetail)

        val result = getUserDetailUseCase.execute("username")

        assertNotNull(result)
        assertEquals(userDetail, result)
    }

    @Test
    fun execute_throwsRuntimeException_whenRepositoryThrowsRuntimeException() = runUnconfinedTest {
        val exception = RuntimeException("Error fetching user details")
        whenever(userRepository.getUserDetails("username")).thenThrow(exception)

        try {
            getUserDetailUseCase.execute("username")
            fail("Expected a RuntimeException to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(exception, e)
        }
    }
}