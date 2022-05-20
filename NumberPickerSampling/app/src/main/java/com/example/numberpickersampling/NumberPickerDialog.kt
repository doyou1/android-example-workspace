package com.example.numberpickersampling

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.numberpickersampling.databinding.DialogNumberPickerBinding
import java.util.*

class NumberPickerDialog(context: Context): Dialog(context) {

    private lateinit var binding: DialogNumberPickerBinding
    private lateinit var calendar: Calendar
    // 현재 연도로부터 과거 몇년까지 선택가능한가
    private val yearCountLimit = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogNumberPickerBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        initNumberPicker()
        setClickEvent()

    }

    // 3개의 NumberPicker
    private fun initNumberPicker() {
        calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        binding.npYear.maxValue = currentYear
        binding.npYear.minValue = currentYear - yearCountLimit
        binding.npYear.value = currentYear

        binding.npMonth.minValue = 1
        binding.npMonth.maxValue = 12
        binding.npMonth.value = currentMonth

        // 년월의 최초 월요일
        calendar.firstDayOfWeek



//        binding.npWeek.minValue = 1
//        binding.npWeek.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//        binding.npWeek.value = currentDay
    }
    private fun setClickEvent() {
        binding.btnCancel.setOnClickListener {
            // cancel
        }
        binding.btnConfirm.setOnClickListener {
            // confirm
        }
    }

}