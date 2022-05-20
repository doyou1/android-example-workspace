package com.example.numberpickersampling

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.numberpickersampling.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnNumberPicker.setOnClickListener {
            startActivity(Intent(this, NumberPickerActivity::class.java))
        }
        binding.btnDatePicker.setOnClickListener {
            startActivity(Intent(this, DatePickerActivity::class.java))
        }
    }


}