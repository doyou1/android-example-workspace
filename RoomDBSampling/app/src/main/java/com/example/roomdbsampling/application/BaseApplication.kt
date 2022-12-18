package com.example.roomdbsampling.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.room.AppDataBase
import com.example.roomdbsampling.util.Const
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

    val historyDao by lazy {
        db.historyDao()
    }

    override fun onCreate() {
        super.onCreate()
        val preferences = getSharedPreferences(Const.TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(Const.TEXT_INIT, false)
        if (!isInit) {
            CoroutineScope(Dispatchers.IO).launch {
                historyDao.insertAll(getInitHistoryList())
                CoroutineScope(Dispatchers.Main).launch {
                    preferences.edit().putBoolean(Const.TEXT_INIT, true).apply()
                }
            }
        }
    }

    private fun getRandomDate(): String {
        val cal = Calendar.getInstance()

        val year = 2022
        cal.set(Calendar.YEAR, year)
        val dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR))
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear)

        return "${cal.get(Calendar.YEAR)}${
            String.format(
                "%02d",
                cal.get(Calendar.MONTH) + 1
            )
        }${String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))}"
    }

    private fun getRandomAsset(type: Int): String {
        val incomeAssets = arrayOf(
            "현금",
            "카드",
            "은행",
            "기타",
        )
        val consumptionAssets = arrayOf(
            "현금",
            "카드",
            "은행",
            "기타"
        )
        val transferAssets = arrayOf(
            "현금",
            "신한은행",
            "미즈호",
            "해외송금",
            "기타",
        )

        return when (type) {
            // income
            0 -> {
                incomeAssets[randBetween(0, incomeAssets.size - 1)]
            }
            // consumption
            1 -> {
                consumptionAssets[randBetween(0, consumptionAssets.size - 1)]
            }
            // transfer
            2 -> {
                transferAssets[randBetween(0, transferAssets.size - 1)]
            }
            else -> throw NotImplementedError()
        }
    }

    private fun getRandomCategory(type: Int): String {
        val incomeCategories = arrayOf(
            "월급",
            "상여",
            "환급",
            "이자",
            "기타",
        )
        val consumptionCategories = arrayOf(
            "식비",
            "교통비",
            "공과금",
            "경조사",
            "옷",
            "사치",
            "적금",
            "기타"
        )
        val transferCategories = arrayOf(
            "부모님",
            "은행간이동",
            "해외송금",
            "기타",
        )

        return when (type) {
            // income
            0 -> {
                incomeCategories[randBetween(0, incomeCategories.size - 1)]
            }
            // consumption
            1 -> {
                consumptionCategories[randBetween(0, consumptionCategories.size - 1)]
            }
            // transfer
            2 -> {
                transferCategories[randBetween(0, transferCategories.size - 1)]
            }
            else -> throw NotImplementedError()
        }
    }

    private fun randBetween(start: Int, end: Int): Int {
        return start + (Math.random() * (end - start)).roundToInt()
    }

    private fun getInitHistoryList(): List<History> {
        val list = ArrayList<History>()
        for (i in 0..1000) {
            val id = i + 1
            val date = getRandomDate()
            // 0:income, 1:consumption, 2:transfer
            val type = i % 3
            val asset = getRandomAsset(type)
            val category = getRandomCategory(type)
            val amount = Random.nextInt(100, 10000)
            val memo = if (i % 3 == 0) null else "memo${String.format("%02d", i)}"

            val item = History(
                id,
                type,
                date,
                asset,
                category,
                amount,
                memo
            )
            list.add(item)
        }
        return list.toList()
    }
}