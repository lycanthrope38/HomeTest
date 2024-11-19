package com.hometest.data.repository

import androidx.paging.PagingSource
import com.hometest.core.BaseTest
import com.hometest.data.api.UserApi
import com.hometest.data.database.UserDao
import com.hometest.data.database.UserEntity
import com.hometest.data.mapper.toUserEntity
import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserDataSourceImplTest : BaseTest() {

    private val api: UserApi = mock()
    private val userDao: UserDao = mock()
    private lateinit var sut: UserDataSource

    @Before
    fun setUp() {
        sut = UserDataSourceImpl(api, userDao)
    }

    @Test
    fun getRemoteUsers_returnsListOfUserResponse_whenApiCallIsSuccessful() = runUnconfinedTest {
        val userResponse = UserResponse(1, "username", "avatarUrl")
        whenever(api.getUsers(any(), any())).thenReturn(listOf(userResponse))

        val result = sut.getRemoteUsers(0, 10)

        assertTrue(result.isSuccess)
        assertEquals(listOf(userResponse), result.getOrNull())
    }

    @Test
    fun getRemoteUsers_returnsError_whenApiCallFails() = runUnconfinedTest {
        whenever(api.getUsers(any(), any())).thenThrow(RuntimeException("Network error"))

        val result = sut.getRemoteUsers(0, 10)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun saveLocalUsers_savesUsersToDatabase() = runUnconfinedTest {
        val userResponse = UserResponse(1, "username", "avatarUrl")
        val userEntity = userResponse.toUserEntity()

        sut.saveLocalUsers(listOf(userResponse))

        verify(userDao).saveUsers(listOf(userEntity))
    }

    @Test
    fun clearLocalUsers_clearsUsersFromDatabase() = runUnconfinedTest {
        sut.clearLocalUsers()

        verify(userDao).clearUsers()
    }

    @Test
    fun getLocalUsers_returnsPagingSource() {
        val pagingSource = mock() as PagingSource<Int, UserEntity>
        whenever(userDao.getUsers()).thenReturn(pagingSource)

        val result = sut.getLocalUsers(0, 10)

        assertEquals(pagingSource, result)
    }

    @Test
    fun getUserDetails_returnsUserDetailResponse_whenApiCallIsSuccessful() = runUnconfinedTest {
        val userDetailResponse = UserDetailResponse(1, "username", "name", "email", "avatarUrl")
        whenever(api.getUserDetails(anyString())).thenReturn(userDetailResponse)

        val result = sut.getUserDetails("username")

        assertTrue(result.isSuccess)
        assertEquals(userDetailResponse, result.getOrNull())
    }

    @Test
    fun getUserDetails_returnsError_whenApiCallFails() = runUnconfinedTest {
        whenever(api.getUserDetails(anyString())).thenThrow(RuntimeException("Network error"))

        val result = sut.getUserDetails("username")

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}