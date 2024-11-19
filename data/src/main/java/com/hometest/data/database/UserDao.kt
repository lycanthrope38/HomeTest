package com.hometest.data.database

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(users: List<UserEntity>)

    @Query("DELETE FROM user")
    suspend fun clearUsers()

    @VisibleForTesting
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @VisibleForTesting
    @Query("SELECT * FROM user")
    suspend fun getUserList(): List<UserEntity>
}
