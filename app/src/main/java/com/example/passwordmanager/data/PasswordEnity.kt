package com.example.passwordmanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountName: String,
    val username: String,
    val encryptedPassword: String
)
