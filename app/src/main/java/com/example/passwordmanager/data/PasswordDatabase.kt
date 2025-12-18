package com.example.passwordmanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(entities = [PasswordEntity::class], version = 2)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun dao(): PasswordDao
}