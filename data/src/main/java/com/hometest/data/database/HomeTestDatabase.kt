package com.hometest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hometest.data.DATABASE_VERSION

@Database(
    entities = [UserEntity::class],
    version = DATABASE_VERSION,
    exportSchema = true,
    autoMigrations = []
)
internal abstract class HomeTestDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}