package com.hometest.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsert(items: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsert(item: T): Long

    @Update
    suspend fun update(item: T): Int

    @Update
    suspend fun update(items: List<T>): Int

    @Delete
    suspend fun delete(item: T): Int

    @Delete
    suspend fun deletes(ids: List<T>)
}