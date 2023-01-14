package com.example.roomdbimagesampling

import android.app.Application
import androidx.room.Room

class BaseApplication: Application() {

    private val TAG = this::class.java.simpleName

    private val DB_NAME = "image"

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val imageDao by lazy {
        db.imageDao()
    }

}