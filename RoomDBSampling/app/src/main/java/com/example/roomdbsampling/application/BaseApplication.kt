package com.example.roomdbsampling.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.room.AppDataBase
import com.example.roomdbsampling.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BaseApplication : Application() {

    private val TAG = this::class.java.simpleName

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
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
        val preferences = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(TEXT_INIT, false)
        if (isInit) {
            checkReservedRegularIncome()
        }
    }

    private fun checkReservedRegularIncome() {
        val preferences = getSharedPreferences(TEXT_LAUNCH_DATE, Context.MODE_PRIVATE)
        val date = preferences.getString(TEXT_LAUNCH_DATE, null)
        val today = getToday()
        CoroutineScope(Dispatchers.IO).launch {
            val list = if (date.isNullOrEmpty()) {
                Log.e(TAG, "date is null")
                historyDao.getReservedRegularIncomeByDate(today)
            } else {
                Log.e(TAG, "$date date is not null")
                historyDao.getReservedRegularIncomeByPeriod(
                    date,
                    today
                )
            }
            CoroutineScope(Dispatchers.Main).launch {
                processReservedRegularIncome(list, today)
                preferences.edit().putString(TEXT_LAUNCH_DATE, today).apply()
            }
        }
    }

    private fun processReservedRegularIncome(list: List<History>, today: String) {
        val targetList = getTargetList(list, today)
        Log.e(TAG, "targetList: $targetList")

        CoroutineScope(Dispatchers.IO).launch {
            for (item in targetList) {
                val id = historyDao.isExistByItem(
                    item.type,
                    item.repeat,
                    item.date
                )
                if (id == 0) {
                    historyDao.insert(item)
                }
            }
        }
    }

    private fun getTargetList(list: List<History>, today: String): List<History> {
        val targetList = ArrayList<History>()
        for (item in list) {
            var prevDate = item.date
            val plusTime = when (item.repeat) {
                FLAG_EVERY_1_MINUTES -> STRING_EVERY_1_MINUTES
                FLAG_EVERY_3_MINUTES -> STRING_EVERY_3_MINUTES
                FLAG_EVERY_5_MINUTES -> STRING_EVERY_5_MINUTES
                FLAG_EVERY_10_MINUTES -> STRING_EVERY_10_MINUTES
                FLAG_EVERY_15_MINUTES -> STRING_EVERY_15_MINUTES
                FLAG_EVERY_30_MINUTES -> STRING_EVERY_30_MINUTES
                FLAG_EVERY_45_MINUTES -> STRING_EVERY_45_MINUTES
                FLAG_EVERY_1_HOURS -> STRING_EVERY_1_HOURS
                FLAG_EVERY_2_HOURS -> STRING_EVERY_2_HOURS
                FLAG_EVERY_6_HOURS -> STRING_EVERY_6_HOURS
                FLAG_EVERY_12_HOURS -> STRING_EVERY_12_HOURS
                else -> throw NotImplementedError()
            }
            while (true) {
                val targetHH = String.format(
                    "%02d",
                    prevDate.substring(8, 10).toInt() + plusTime.substring(0, 2).toInt()
                )
                val targetMm = String.format(
                    "%02d",
                    prevDate.substring(10, 12).toInt() + plusTime.substring(2, 4).toInt()
                )
                var targetDate = "${prevDate.substring(0, 8)}$targetHH$targetMm"
                val sdf = SimpleDateFormat("yyyyMMddHHmm")
                val cal = sdf.parse(targetDate)
                targetDate = sdf.format(cal.time)
                Log.e(TAG, "$today $targetDate ${sdf.parse(targetDate).after(sdf.parse(today))}")
                if (sdf.parse(targetDate).after(sdf.parse(today))) break
                val targetHistory = History(
                    0,
                    item.type,
                    item.repeat,
                    targetDate,
                    item.assetId,
                    item.assetName,
                    item.categoryId,
                    item.categoryName,
                    item.amount,
                    item.memo
                )

                targetList.add(targetHistory)
                prevDate = targetDate
            }
        }
        return targetList
    }

    private fun getToday(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        return sdf.format(cal.time)
    }

}