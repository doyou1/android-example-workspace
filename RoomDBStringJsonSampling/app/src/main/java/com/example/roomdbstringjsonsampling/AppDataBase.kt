package com.example.roomdbstringjsonsampling

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}