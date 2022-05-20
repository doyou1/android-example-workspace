package com.example.numberpickersampling

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.numberpickersampling.databinding.ActivityDatePickerBinding
import com.example.numberpickersampling.databinding.ActivityMainBinding
import java.util.*

class DatePickerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDatePickerBinding
    private lateinit var datePickerDialog: DatePickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker)

        initNumberPicker()
        binding.btnDatePicker.text = getTodayDate()
        binding.btnDatePicker.setOnClickListener {
            showNumberPicker()
        }
    }

    private fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return makeDateString(day, month, year)
    }

    private fun initNumberPicker() {
        val numberSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            val month = month + 1
            val date = makeDateString(dayOfMonth, month, year)
            binding.btnDatePicker.text = date
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, numberSetListener, year, month, day)
    }

    private fun makeDateString(dayOfMonth: Int, month: Int, year: Int): String {
        return "${getMonthFormat(month)} $dayOfMonth $year"
    }

    private fun getMonthFormat(month: Int): String {
        return when(month) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "JAN"
        }
    }


    private fun showNumberPicker() {
        datePickerDialog.show()
    }
}