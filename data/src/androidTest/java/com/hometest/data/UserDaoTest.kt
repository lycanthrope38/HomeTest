package com.hometest.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hometest.core.BaseTest
import com.hometest.data.database.HomeTestDatabase
import com.hometest.data.database.UserDao
import com.hometest.data.database.UserEntity
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserDaoTest : BaseTest() {

    private lateinit var sut: UserDao
    private lateinit var homeTestDatabase: HomeTestDatabase

    @Before
    fun setUp() {
        homeTestDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HomeTestDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = homeTestDatabase.userDao()
    }

    @After
    fun tearDown() {
        homeTestDatabase.close()
    }

    @Test
    fun getUsers_returnsPagingSource() = runUnconfinedTest {
        val mockUsers = (1L..20L).map {
            UserEntity(it, "username$it", "avatarUrl$it", "htmlUrl$it")
        }

        sut.saveUsers(mockUsers)

        val pagingSource = sut.getUsers()
        val pager = TestPager(PagingConfig(pageSize = MAX_PAGE_SIZE), pagingSource)
        val result = pager.refresh() as PagingSource.LoadResult.Page

        assert(mockUsers.containsAll(result.data))
    }

    @Test
    fun saveUsers_insertsUsersIntoDatabase() = runUnconfinedTest {
        val users = listOf(UserEntity(1, "username", "avatarUrl", "htmlUrl"))
        sut.saveUsers(users)

        val user = sut.getUserById(1)
        assertTrue(user == users[0])
    }

    @Test
    fun clearUsers_deletesAllUsersFromDatabase() = runUnconfinedTest {
        val users = listOf(UserEntity(1, "username", "avatarUrl", "htmlUrl"))
        sut.saveUsers(users)
        sut.clearUsers()

        val userList = sut.getUserList()
        assertTrue(userList.isEmpty())
    }
}