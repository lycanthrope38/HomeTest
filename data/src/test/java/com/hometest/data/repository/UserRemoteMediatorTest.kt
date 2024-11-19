@file:OptIn(ExperimentalPagingApi::class)

package com.hometest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hometest.core.BaseTest
import com.hometest.data.MAX_PAGE_SIZE
import com.hometest.data.database.UserEntity
import com.hometest.data.model.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRemoteMediatorTest : BaseTest() {

    private val userDataSource: UserDataSource = mock()
    private lateinit var sut: UserRemoteMediator

    @Before
    fun setUp() {
        sut = UserRemoteMediator(userDataSource)
    }

    @Test
    fun load_returnsSuccess_whenLoadTypeIsRefreshAndDataIsLoaded() = runUnconfinedTest {
        val pagingState = PagingState<Int, UserEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = MAX_PAGE_SIZE),
            leadingPlaceholderCount = 0
        )
        val userResponse = UserResponse(1, "username", "avatarUrl")
        whenever(userDataSource.getRemoteUsers(0, MAX_PAGE_SIZE)).thenReturn(
            Result.success(
                listOf(
                    userResponse
                )
            )
        )
        whenever(userDataSource.saveLocalUsers(any())).thenReturn(Unit)
        whenever(userDataSource.clearLocalUsers()).thenReturn(Unit)

        val result = sut.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        verify(userDataSource).clearLocalUsers()
        verify(userDataSource).saveLocalUsers(any())
    }

    @Test
    fun load_returnsSuccess_whenLoadTypeIsAppendAndNoMoreData() = runUnconfinedTest {
        val pagingState = PagingState<Int, UserEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = MAX_PAGE_SIZE),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun load_returnsError_whenDataSourceReturnsError() = runUnconfinedTest {
        val pagingState = PagingState<Int, UserEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = MAX_PAGE_SIZE),
            leadingPlaceholderCount = 0
        )
        whenever(
            userDataSource.getRemoteUsers(
                any(),
                any()
            )
        ).thenReturn(Result.failure(Exception("Network error")))

        val result = sut.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertEquals(
            "Network error",
            (result as RemoteMediator.MediatorResult.Error).throwable.message
        )
    }

    @Test
    fun load_returnsSuccess_whenLoadTypeIsPrependAndNoMoreData() = runUnconfinedTest {
        val pagingState = PagingState<Int, UserEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = MAX_PAGE_SIZE),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.PREPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}
