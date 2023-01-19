package com.example.datepickerproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.datepickerproject.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private val prevTag = "Prev Date Picker"
    private val afterTag = "After Date Picker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDateText()
    }

    private fun initDateText() {
        binding.etPrev.setText(getCurrentDate())    // 2022-02-16
        binding.etAfter.setText(getCurrentDate())   // 2022-02-16
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.etPrev.setOnClickListener {
            DatePickerFragment(binding.etPrev).show(supportFragmentManager, prevTag)
        }
        binding.etAfter.setOnClickListener {
            DatePickerFragment(binding.etAfter).show(supportFragmentManager, afterTag)
        }
    }

    fun setDate(value: String, tag: String?) {
        if (tag == prevTag) {
            binding.etPrev.setText(value)
            val after = binding.etAfter.text.toString()
            if (getTime(value) >= getTime(after)) {
                binding.etAfter.setText(computeDate(value, 1))
            }
        } else if (tag == afterTag) {
            binding.etAfter.setText(value)
            val prev = binding.etPrev.text.toString()
            if (getTime(value) <= getTime(prev)) {
                binding.etPrev.setText(computeDate(value, -1))
            }
        }
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(cal.time)
    }

    private fun getTime(date: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.parse(date).time
    }

    private fun computeDate(date: String, num: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(date)
        cal.add(Calendar.DAY_OF_MONTH, num)
        return sdf.format(cal.time)
    }
}