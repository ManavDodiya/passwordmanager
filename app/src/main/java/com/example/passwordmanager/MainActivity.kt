package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.passwordmanager.data.PasswordDatabase
import com.example.passwordmanager.ui.home.HomeScreen
import com.example.passwordmanager.viewmodel.PasswordViewModel
import com.example.passwordmanager.viewmodel.PasswordViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val database = Room.databaseBuilder(
            applicationContext,
            PasswordDatabase::class.java,
            "password_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()

        setContent {
            val viewModel: PasswordViewModel = viewModel(
                factory = PasswordViewModelFactory(database.dao())
            )
            HomeScreen(viewModel = viewModel)
        }
    }
}