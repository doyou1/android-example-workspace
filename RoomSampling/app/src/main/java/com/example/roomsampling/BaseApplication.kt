package com.example.roomsampling

import android.app.Application
import androidx.room.Room

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }
    val userDao by lazy {
        db.userDao()
    }

}