package com.example.calendarwithrecyclerviewsampling

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendarwithrecyclerviewsampling.databinding.ActivityMainBinding
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
    }

    private fun setRecyclerView() {


        // head(7) + content(35)
        val headList = arrayListOf<CalendarItem>(
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0),
            CalendarItem(TYPE_CALENDAR_HEAD, "", 0, 0, 0)
        )

        val contentList = arrayListOf<CalendarItem>()
        // 현재 달의 1일의 요일
        val dateList = DateUtil.getDateList()
        for (strDate in dateList) {
            Log.e(TAG, strDate)
            val consumption = nextInt(0, 100000)
            val income = nextInt(0, 100000)
            val result = income - consumption
            contentList.add(
                CalendarItem(
                    TYPE_CALENDAR_CONTENT,
                    strDate,
                    consumption,
                    income,
                    result
                )
            )
        }

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(applicationContext, 7)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                binding.recyclerView.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(applicationContext, 7)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.recyclerView.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = ArrayList<CalendarItem>()
        list.addAll(headList)
        list.addAll(contentList)
        binding.recyclerView.adapter = RVAdapter(list)
    }
}