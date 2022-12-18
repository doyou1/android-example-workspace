package com.example.roomdbsampling.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdbsampling.dao.AssetDao
import com.example.roomdbsampling.dao.CategoryDao
import com.example.roomdbsampling.dao.HistoryDao
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History


@Database(entities = [History::class, Asset::class, Category::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun assetDao(): AssetDao
    abstract fun categoryDao(): CategoryDao
}
