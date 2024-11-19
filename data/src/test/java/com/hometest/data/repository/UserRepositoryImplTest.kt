package com.hometest.data.repository

import com.hometest.core.BaseTest
import com.hometest.data.mapper.toUserDetail
import com.hometest.data.model.UserDetailResponse
import com.hometest.domain.repository.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UserRepositoryImplTest : BaseTest() {

    private val userDataSource: UserDataSource = mock()
    private val userRemoteMediator: UserRemoteMediator = UserRemoteMediator(userDataSource)
    private val userRepository: UserRepository =
        UserRepositoryImpl(userDataSource, userRemoteMediator)

    @Test
    fun getUserDetails_returnsUserDetail_whenResultIsSuccess() = runUnconfinedTest {
        val userDetailResponse = UserDetailResponse(1, "username", "name", "email", "bio")
        whenever(userDataSource.getUserDetails("username")).thenReturn(
            Result.success(
                userDetailResponse
            )
        )

        val result = userRepository.getUserDetails("username")

        assertNotNull(result)
        assertEquals(userDetailResponse.toUserDetail(), result)
    }

    @Test
    fun getUserDetails_returnsException_whenResultIsNotSuccess() = runUnconfinedTest {
        val exception = Exception("User not found")
        whenever(userDataSource.getUserDetails("username")).thenReturn(Result.failure(exception))

        try {
            userRepository.getUserDetails("username")
            fail("Expected an Exception to be thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}