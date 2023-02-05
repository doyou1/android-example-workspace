package com.example.pedometerclone.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Steps::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun stepsDao(): StepsDao
}

