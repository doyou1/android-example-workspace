package com.example.numberpickersampling

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.numberpickersampling.databinding.DialogNumberPickerBinding
import java.text.SimpleDateFormat
import java.util.*

class NumberPickerDialog(private val _context: Context) : Dialog(_context) {

    private lateinit var binding: DialogNumberPickerBinding
    private lateinit var calendar: Calendar

    private val TAG = this::class.java.simpleName

    private val yearCountLimit = 20

    // mCurrent'Year' use for 'month' number picker
    private var mCurrentYear = -1

    // mCurrent'Month' use for 'year' number picker
    private var mCurrentMonth = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNumberPickerBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        initNumberPicker()
        setClickEvent()
        setNumberPickerValueChangeEvent()
    }

    // NumberPicker 초기화
    private fun initNumberPicker() {
        calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        // number picker for year
        binding.npYear.maxValue =
            currentYear // Set current year to maximum because future data cannot be retrieved
        binding.npYear.minValue =
            currentYear - yearCountLimit  // range is (currentYear - yearCountLimit) ~ currentYear
        binding.npYear.value = currentYear

        // number picker for month
        binding.npMonth.minValue = 1
        binding.npMonth.maxValue = 12
        binding.npMonth.value = currentMonth

        mCurrentYear = currentYear
        mCurrentMonth = currentMonth
        // get week number picker value
        val period: ArrayList<String> = getWeeksOfMonth(currentYear, currentMonth)
        // If you change displayedValues with displayedValues not null - ArrayIndexOutOfBoundsException occurs
        binding.npWeek.displayedValues = null
        binding.npWeek.minValue = 0
        binding.npWeek.maxValue = period.size - 1

        binding.npWeek.displayedValues = period.toTypedArray()
    }

    // 클릭 이벤트 설정
    private fun setClickEvent() {
        binding.btnCancel.setOnClickListener {
            // cancel
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            // confirm
            val prevYear = binding.npYear.value
            val prevMonth = String.format("%02d", binding.npMonth.value)
            val period = binding.npWeek.displayedValues[binding.npWeek.value]
            val prevDay = period.split("~")[0]
            val lastDay = period.split("~")[1]

            val lastYearAndLastMonth = if ((binding.npWeek.maxValue == binding.npWeek.value) && isOverMonth(prevYear, prevMonth.toInt(), lastDay.toInt())) getLastYearAndLastMonth(prevYear, prevMonth.toInt())
            else "$prevYear$prevMonth"
            val lastYear = lastYearAndLastMonth.substring(0, 4)
            val lastMonth = lastYearAndLastMonth.substring(4, 6)
            Log.e(
                TAG,
                "$prevYear/$prevMonth/$prevDay ~ $lastYear/$lastMonth/$lastDay"
            )
            val value = "$prevYear/$prevMonth/$prevDay ~ $lastYear/$lastMonth/$lastDay"

            // java.lang.ClassCastException: android.view.ContextThemeWrapper cannot be cast to com.example.numberpickersampling.NumberPickerActivity
            // (context as NumberPickerActivity).setButtonText(value)
            (_context as NumberPickerActivity).setButtonText(value)
            dismiss()
        }
    }

    private fun setNumberPickerValueChangeEvent() {
        binding.npYear.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrentYear = newVal
            // Change of week period according to change of year and month value
            val period: ArrayList<String> = getWeeksOfMonth(mCurrentYear, mCurrentMonth)
            // If you change displayedValues with displayedValues not null - ArrayIndexOutOfBoundsException occurs
            binding.npWeek.displayedValues = null
            binding.npWeek.minValue = 0
            binding.npWeek.maxValue = period.size - 1
            binding.npWeek.displayedValues = period.toTypedArray()
        }
        binding.npMonth.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrentMonth = newVal
            // Change of week period according to change of year and month value
            val period: ArrayList<String> = getWeeksOfMonth(mCurrentYear, mCurrentMonth)
            // If you change displayedValues with displayedValues not null - ArrayIndexOutOfBoundsException occurs
            binding.npWeek.displayedValues = null
            binding.npWeek.minValue = 0
            binding.npWeek.maxValue = period.size - 1
            binding.npWeek.displayedValues = period.toTypedArray()
        }
    }

    // 특정 연월에 해당하는 주간 데이터 추출
    private fun getWeeksOfMonth(year: Int, month: Int): ArrayList<String> {
        val calendar = Calendar.getInstance()   // Calendar 객체 생성
        calendar.set(year, month - 1, 1)
        calendar.set(
            Calendar.WEEK_OF_MONTH,
            1
        )             // Set the first week of a particular month
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)  // Set a Monday of a particular week

        // For the first Monday of January, if the first Monday is in the previous month, an error occurs that moves on to the previous year.
        // The year has changed in the process of setting up the first Monday.
        if (year != calendar.get(Calendar.YEAR)) {
            calendar.set(year, month - 1, 1)
            // If we set 2, it is possible to extract the "first week" of the specific month we want to obtain without the previous year.
            calendar.set(Calendar.WEEK_OF_MONTH, 2)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        // For reasons similar to the above conditional statement, if the first Monday of the week is in the previous month, an error occurs that passes to the previous month.
        // The month has changed in the process of setting up the first Monday.
        if (month != calendar.get(Calendar.MONTH) + 1) {
            calendar.set(year, month - 1, 1)
            // If we set 2, it is possible to extract the "first week" of the specific month we want to obtain that does not include the previous month.
            calendar.set(Calendar.WEEK_OF_MONTH, 2)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        // Date of the first Monday of a particular year, month
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        // Originally, if the value of the variable is "1", it means the first week, but "2" also means the first week, depending on the previous year, the previous month.
        val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)

        return makeWeeksOfMonthByString(calendar, year, month, day, weekOfMonth)
    }

    private fun makeWeeksOfMonthByString(
        calendar: Calendar,
        year: Int,
        month: Int,
        day: Int,
        weekOfMonth: Int
    ): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("${String.format("%02d", day)}~${String.format("%02d", day + 6)}") // 일주일은 N일~N+6일
        // 첫째주 기간 추가
        var newWeekOfMonth = weekOfMonth    //(variable) val to var weekOfMonth(parameter)

        // Until the month is over,
        while (true) {
            newWeekOfMonth++
            calendar.set(Calendar.WEEK_OF_MONTH, newWeekOfMonth)

            // when the month is over, process last period
            // Day numeric values based on 29~35 and 31~37 months (parameter) are extracted.
            if (month != calendar.get(Calendar.MONTH)) {
                // String convert to solve this problem
                val dayOfNextMonday = calendar.get(Calendar.DAY_OF_MONTH)

                // If you have moved on to the next month but Monday is the 1st
                // Adding data in the format "${N}~${N+6}" has successfully added the end date of a specific month to ${N+6}
                // No extra work required -> break
                if (dayOfNextMonday == 1) break

                val dayOfSundayOfLastWeek = (dayOfNextMonday - 1)
                val waveIndex = list[list.size - 1].indexOf("~")
                // "${N}" in the form of "${N}~${N+6}"
                val frontDayOfWave = list[list.size - 1].substring(0, waveIndex)
                // Enter the appropriate period string in the last data of the already entered list
                // ex) 29~35 -> 29~04,
                list[list.size - 1] =
                    "$frontDayOfWave~${String.format("%02d", dayOfSundayOfLastWeek)}"

                // I added it to the last period of the month, so end the while statement
                break
            }

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            list.add("${String.format("%02d", day)}~${String.format("%02d", day + 6)}")
        }
        return list
    }

    private fun isOverMonth(prevYear: Int, prevMonth: Int, lastDay: Int): Boolean {
        val cal = Calendar.getInstance()
        cal.set(prevYear, prevMonth - 1, 1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH) != lastDay
    }

    private fun getLastYearAndLastMonth(prevYear: Int, prevMonth: Int): String {
        val cal = Calendar.getInstance()
        cal.set(prevYear, prevMonth - 1, 1)
        cal.add(Calendar.MONTH, 1)

        return SimpleDateFormat("yyyyMM").format(cal.time)
    }

}