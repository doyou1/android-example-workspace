package com.example.roomdbsampling.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivitySplashBinding
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.util.InitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import com.example.roomdbsampling.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(TEXT_INIT, false)
        if (isInit) {
            checkReservedRegularIncome()
            startMainActivity()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                (application as BaseApplication).assetDao.insertAll(getInitAssetList())
                (application as BaseApplication).categoryDao.insertAll(getInitCategoryList())
                val assetSize = (application as BaseApplication).assetDao.getSize()
                val categorySize = (application as BaseApplication).categoryDao.getSize()
                (application as BaseApplication).historyDao.insertAll(
                    getInitHistoryList(
                        assetSize,
                        categorySize
                    )
                )
                CoroutineScope(Dispatchers.Main).launch {
                    preferences.edit().putBoolean(TEXT_INIT, true).apply()
                    startMainActivity()
                }
            }
        }
    }

    private fun checkReservedRegularIncome() {
        val preferences = getSharedPreferences(TEXT_LAUNCH_DATE, Context.MODE_PRIVATE)
        val date = preferences.getString(TEXT_LAUNCH_DATE, null)
        val today = getToday()
        CoroutineScope(Dispatchers.IO).launch {
            val list = if (date.isNullOrEmpty()) {
                Log.e(TAG, "date is null")
                (application as BaseApplication).historyDao.getReservedRegularIncomeByDate(today)
            } else {
                Log.e(TAG, "$date date is not null")
                (application as BaseApplication).historyDao.getReservedRegularIncomeByPeriod(
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
                val id = (application as BaseApplication).historyDao.isExistByItem(
                    item.type,
                    item.repeat,
                    item.date
                )
                if (id == 0) {
                    (application as BaseApplication).historyDao.insert(item)
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

    private fun startMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getInitAssetList(): List<Asset> {
        val list = ArrayList<Asset>()

        var count = 0
        for (name in INIT_INCOME_ASSETS) {
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

        for (name in INIT_CONSUMPTION_ASSETS) {
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

        for (name in INIT_TRANSFER_ASSETS) {
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
        for (name in INIT_INCOME_CATEGORIES) {
            val item = Category(
                0,
                0,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_CONSUMPTION_CATEGORIES) {
            val item = Category(
                0,
                1,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_TRANSFER_CATEGORIES) {
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
            val repeat = InitUtil.randBetween(-1, 10)
            val assetId = InitUtil.randBetween(1, assetSize)
            val assetName = (application as BaseApplication).assetDao.getNameByid(assetId)
            val categoryId = InitUtil.randBetween(1, categorySize)
            val categoryName = (application as BaseApplication).categoryDao.getNameByid(categoryId)

            val amount = Random.nextInt(100, 10000)
            val memo = if (i % 3 == 0) null else "memo${String.format("%02d", i)}"

            val item = History(
                id,
                type,
                repeat,
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