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
        binding = ActivityDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
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
        val numberSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                val month = month + 1
                val date = makeDateString(dayOfMonth, month, year)
                binding.tvNumber.text = "Date: $date"
                binding.btnDatePicker.text = date
            }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, numberSetListener, year, month, day)
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", datePickerDialog)
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog)

    }

    private fun makeDateString(dayOfMonth: Int, month: Int, year: Int): String {
        return "${String.format("%04d", year)}/${String.format("%02d", month)}/${String.format("%02d", dayOfMonth)}"
    }

    private fun showNumberPicker() {
        datePickerDialog.show()
    }
}