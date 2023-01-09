package com.example.regularincomeandinstallmentsampling.room

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncome
import com.example.regularincomeandinstallmentsampling.util.*
import com.example.regularincomeandinstallmentsampling.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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

    override fun onCreate() {
        super.onCreate()
        val pref = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val init = pref.getBoolean(TEXT_INIT, false)
        if (!init) {
            initRandomData()
            pref.edit().putBoolean(TEXT_INIT, true).apply()
        } else {
            reservedRegularIncome()
        }

    }

    private fun initRandomData() {

        val list = arrayListOf<RegularIncome>()
        for (i in 0..100) {
            val item = RegularIncome(
                0,
                (-1..10).random(),
                Util.getRandomDate(),
                "Name$i",
                (100..10000).random(),
                true
            )
            Log.e(TAG, "item: $item")
            list.add(item)
        }
        CoroutineScope(Dispatchers.IO).launch {
            regularIncomeDao.insertAll(list)
        }
    }


    private fun reservedRegularIncome() {
        // yyyyMMdd
        val today = Util.getToday()
        CoroutineScope(Dispatchers.IO).launch {
            val list = regularIncomeDao.getLatestItems(today)
            CoroutineScope(Dispatchers.Main).launch {
                val map = processRegularIncome(list, today)
                val prev = map[TEXT_PREV]
                val target = map[TEXT_TARGET]
                Log.e(TAG, "prev: $prev")
                Log.e(TAG, "target: $target")

                CoroutineScope(Dispatchers.IO).launch {
                    target?.let {
                        regularIncomeDao.insertAll(it)
                    }
                    prev?.let {
                        regularIncomeDao.updateAll(prev)
                    }
                }
            }
        }
    }

    private fun processRegularIncome(
        list: List<RegularIncome>,
        today: String
    ): HashMap<String, List<RegularIncome>> {

        val temp = arrayListOf<RegularIncome>()
        for (item in list) {
            var prevDate = item.date

            while (true) {
                val period = Util.getPeriod(item.period)
                val plusDate = Util.getPlusDate(item.period)
                val targetDate = Util.getTargetDate(prevDate, plusDate)

                Log.e(
                    TAG,
                    "($period) prevDate + plusDate =  targetDate: $prevDate + $plusDate = $targetDate"
                )

                // can insert : targetDate <= today
                if (Util.compareDate(targetDate, today, SimpleDateFormat("yyyyMMdd"))) temp.add(
                    RegularIncome(item.id, item.period, targetDate, item.name, item.amount, false)
                )
                // can't insert : targetDate > today
                else break
                prevDate = targetDate

            }
        }


        val prev = arrayListOf<RegularIncome>()
        val target = arrayListOf<RegularIncome>()

        for (item in list) {
            val filter = temp.filter { v -> v.id == item.id }.sortedBy { v -> v.date }
            if (filter.isNotEmpty()) {
                // update
                prev.add(
                    RegularIncome(
                        item.id,
                        item.period,
                        item.date,
                        item.name,
                        item.amount,
                        false
                    )
                )

                val result = arrayListOf<RegularIncome>()
                for (f in filter) {
                    result.add(RegularIncome(
                        0,
                        f.period,
                        f.date,
                        f.name,
                        f.amount,
                        false
                    ))
                }

                // last item -> isNewFlag = true
                result[result.size - 1].isNewFlag = true
                target.addAll(result)
            }
        }

        val map = hashMapOf<String, List<RegularIncome>>()
        map[TEXT_PREV] = prev
        map[TEXT_TARGET] = target

        return map
    }
}