package com.example.contentproviderprojecta

import android.app.Application

class BaseApplication: Application() {

    lateinit var mDatabase: DBHelper

    override fun onCreate() {
        super.onCreate()

        mDatabase = DBHelper(this, "newdb.db", 1)
        mDatabase.setDelete()   // delete all items

        // set data
        for (item in LIST) {
            mDatabase.setItem(item.content, item.name, item.num)
        }

    }
}