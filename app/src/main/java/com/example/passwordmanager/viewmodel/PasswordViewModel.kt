package com.example.passwordmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.crypto.CryptoManager
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class PasswordViewModel(private val dao: PasswordDao) : ViewModel() {


    val passwords = dao.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    fun addPassword(
        account: String,
        username: String,
        password: String
    ) {
        if (account.isBlank() || username.isBlank() || password.isBlank()) return

        viewModelScope.launch {
            dao.insert(
                PasswordEntity(
                    accountName = account,
                    username = username,
                    encryptedPassword = CryptoManager.encrypt(password)
                )
            )
        }
    }

    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch {
            dao.delete(password)
        }
    }

    fun updatePassword(updated: PasswordEntity) {
        viewModelScope.launch {
            dao.update(updated)
        }
    }

}