package com.example.pedometersampling

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.room.PedometerDao

@Database(entities = [Pedometer::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pedometerDao(): PedometerDao
}