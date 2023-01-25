package com.example.multifragmentswipeeventsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.multifragmentswipeeventsampling.databinding.ActivityMainBinding
import com.example.multifragmentswipeeventsampling.fragment.DayFragment
import com.example.multifragmentswipeeventsampling.fragment.MonthFragment
import com.example.multifragmentswipeeventsampling.fragment.WeekFragment
import com.example.multifragmentswipeeventsampling.fragment.YearFragment
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private var currentDate: String? = null
    private var currentPosition: Int? = null
    private lateinit var onSwipeTouchListener: OnSwipeTouchListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTabLayout()

    }

    override fun onResume() {
        super.onResume()
        initDate()
        initFrameLayout()
        setClickEvent()
    }

    private fun initTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Day"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Month"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Week"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Year"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
        })
    }

    private fun initFrameLayout() {
        setFragment(0)

        // handleHorizontalSwipe
        onSwipeTouchListener = object :
            OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeftToRight() {
                super.onSwipeLeftToRight()
                setPrevDate()

            }

            override fun onSwipeRightToLeft() {
                super.onSwipeRightToLeft()
                setNextDate()
            }
        }

        binding.frameLayout.setOnTouchListener(onSwipeTouchListener)
    }

    private fun initDate() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        currentDate = sdf.format(calendar.time)
        binding.tvDate.text = currentDate
    }

    private fun setClickEvent() {
        binding.btnPrev.setOnClickListener {
            setPrevDate()
        }

        binding.btnNext.setOnClickListener {
            setNextDate()
        }
    }

    private fun setPrevDate() {
        ifLet(currentDate, currentPosition) { (date, position) ->
            currentDate = computeDate(date as String, position as Int, -1)
            binding.tvDate.text = currentDate
            val currentPosition = binding.tabLayout.selectedTabPosition
            binding.tabLayout.getTabAt(currentPosition)?.select()
        }
    }

    private fun setNextDate() {
        ifLet(currentDate, currentPosition) { (date, position) ->
            currentDate = computeDate(date as String, position as Int, 1)
            binding.tvDate.text = currentDate
            val currentPosition = binding.tabLayout.selectedTabPosition
            binding.tabLayout.getTabAt(currentPosition)?.select()
        }
    }

    private fun setFragment(position: Int?) {
        position?.let {
            currentPosition = position
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = when (it) {
                0 -> DayFragment(currentDate)
                1 -> MonthFragment(currentDate)
                2 -> WeekFragment(currentDate)
                3 -> YearFragment(currentDate)
                else -> throw NotImplementedError()
            }
            transaction.replace(binding.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun computeDate(date: String, position: Int, plusNum: Int): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val date = sdf.parse(date)
        val cal = Calendar.getInstance()
        cal.time = date

        when (position) {
            0 ->
                // Day
                cal.add(Calendar.DAY_OF_MONTH, plusNum)

            1 ->
                // Month
                cal.add(Calendar.MONTH, plusNum)

            2 ->
                // Week
                cal.add(Calendar.WEEK_OF_MONTH, plusNum)

            3 ->
                // Year
                cal.add(Calendar.YEAR, plusNum)
        }
        return sdf.format(cal.time)
    }

    inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
        if (elements.all { it != null }) {
            closure(elements.filterNotNull())
        }
    }
}