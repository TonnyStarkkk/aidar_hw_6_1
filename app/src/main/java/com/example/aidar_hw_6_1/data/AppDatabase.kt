package com.example.aidar_hw_6_1.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao

}