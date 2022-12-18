package com.example.roomdbsampling.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdbsampling.dao.HistoryDao
import com.example.roomdbsampling.entity.History


@Database(entities = [History::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
