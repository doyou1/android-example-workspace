package com.example.roomdbsampling.application

import android.app.Application
import androidx.room.Room
import com.example.roomdbsampling.room.AppDataBase
import com.example.roomdbsampling.util.Const

class BaseApplication: Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, Const.DB_NAME).build()
    }

    val historyDao by lazy {
        db.historyDao()
    }
}