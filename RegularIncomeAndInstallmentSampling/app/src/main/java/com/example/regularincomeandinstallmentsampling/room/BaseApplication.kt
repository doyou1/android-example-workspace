package com.example.regularincomeandinstallmentsampling.room

import android.app.Application
import androidx.room.Room
import com.example.regularincomeandinstallmentsampling.util.DB_NAME

class BaseApplication : Application() {

    private val TAG = this::class.java.simpleName

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val regularIncomeDao by lazy {
        db.regularIncomeDao()
    }

    val installmentDao by lazy {
        db.installmentDao()
    }

}