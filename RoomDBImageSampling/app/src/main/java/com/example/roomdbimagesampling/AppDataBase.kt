package com.example.roomdbimagesampling

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Image::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}