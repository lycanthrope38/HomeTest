package com.hometest.data

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.hometest.core.BaseTest
import com.hometest.data.database.UserEntity
import com.hometest.data.repository.UserDataSource
import com.hometest.data.repository.UserRemoteMediator
import com.hometest.data.repository.UserRepositoryImpl
import com.hometest.domain.repository.UserRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UserRepositoryImplTest : BaseTest() {

    private val userDataSource: UserDataSource = mock()
    private val userRemoteMediator: UserRemoteMediator = UserRemoteMediator(userDataSource)
    private val userRepository: UserRepository =
        UserRepositoryImpl(userDataSource, userRemoteMediator)

    @Test
    fun getUsers_returnsUsersSuccess() = runUnconfinedTest {

        val userResponses = (1L..100).map {
            UserEntity(it, "login$it", "avatarUrl$it", "htmlUrl$it")
        }

        val pagingSourceFactory = userResponses.asPagingSourceFactory()
        val pagingSource = pagingSourceFactory()
        whenever(userDataSource.getLocalUsers(0, 10)).thenReturn(pagingSource)
        val users = userRepository.getUsers(0, 10)
        val usersSnapshot = users.asSnapshot { scrollTo(index = 50) }
        assert(usersSnapshot.map { it.login }.containsAll((40..50).map { "login$it" }))
    }
}