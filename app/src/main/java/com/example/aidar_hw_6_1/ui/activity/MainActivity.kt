package com.example.aidar_hw_6_1.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.room.Room
import com.example.aidar_hw_6_1.data.AppDatabase
import com.example.aidar_hw_6_1.data.MIGRATION_1_2
import com.example.aidar_hw_6_1.ui.screens.ProfileScreen
import com.example.aidar_hw_6_1.ui.screens.ProfileScreenViewModel
import com.example.aidar_hw_6_1.ui.theme.Aidar_hw_6_1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "profile"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        val viewModel = ProfileScreenViewModel(db)

        setContent {
            Aidar_hw_6_1Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreen(viewModel)
                }
            }
        }
    }
}




