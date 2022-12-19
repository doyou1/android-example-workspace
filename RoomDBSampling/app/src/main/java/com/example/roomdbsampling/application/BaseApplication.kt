package com.example.roomdbsampling.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.room.AppDataBase
import com.example.roomdbsampling.util.Const
import com.example.roomdbsampling.util.InitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class BaseApplication : Application() {

    private val TAG = this::class.java.simpleName

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, Const.DB_NAME).build()
    }

    val assetDao by lazy {
        db.assetDao()
    }

    val categoryDao by lazy {
        db.categoryDao()
    }

    val historyDao by lazy {
        db.historyDao()
    }

    override fun onCreate() {
        super.onCreate()
        val preferences = getSharedPreferences(Const.TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(Const.TEXT_INIT, false)
        if (!isInit) {
            CoroutineScope(Dispatchers.IO).launch {
                assetDao.insertAll(getInitAssetList())
                categoryDao.insertAll(getInitCategoryList())
                val assetSize = assetDao.getSize()
                val categorySize = categoryDao.getSize()
                historyDao.insertAll(getInitHistoryList(assetSize, categorySize))
                CoroutineScope(Dispatchers.Main).launch {
                    preferences.edit().putBoolean(Const.TEXT_INIT, true).apply()
                }
            }
        }
    }

    private fun getInitAssetList(): List<Asset> {
        val list = ArrayList<Asset>()

        var count = 0
        for (name in Const.INIT_INCOME_ASSETS) {
            val item = Asset(
                0,
                0,
                name,
                Random.nextInt(0, 100000),
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in Const.INIT_CONSUMPTION_ASSETS) {
            val item = Asset(
                0,
                1,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in Const.INIT_TRANSFER_ASSETS) {
            val item = Asset(
                0,
                2,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitCategoryList(): List<Category> {
        val list = ArrayList<Category>()

        var count = 0
        for (name in Const.INIT_INCOME_CATEGORIES) {
            val item = Category(
                0,
                0,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in Const.INIT_CONSUMPTION_CATEGORIES) {
            val item = Category(
                0,
                1,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in Const.INIT_TRANSFER_CATEGORIES) {
            val item = Category(
                0,
                2,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitHistoryList(assetSize: Int, categorySize: Int): List<History> {
        val list = ArrayList<History>()
        for (i in 0..10000) {
            val id = i + 1
            val date = InitUtil.getRandomDate()
            // 0:income, 1:consumption, 2:transfer
            val type = i % 3

            val assetId = InitUtil.randBetween(1, assetSize)
            val assetName = assetDao.getNameByid(assetId)

            val categoryId = InitUtil.randBetween(1, categorySize)
            val categoryName = categoryDao.getNameByid(categoryId)

            val amount = Random.nextInt(100, 10000)
            val memo = if (i % 3 == 0) null else "memo${String.format("%02d", i)}"

            val item = History(
                id,
                type,
                date,
                assetId,
                assetName,
                categoryId,
                categoryName,
                amount,
                memo
            )
            Log.e(TAG, "item: $item")
            list.add(item)
        }
        return list.toList()
    }
}