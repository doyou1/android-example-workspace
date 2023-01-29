package com.example.numberpickersampling

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.numberpickersampling.databinding.ActivityNumberPickerBinding

class NumberPickerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumberPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number_picker)

        binding.btnNumberPicker.setOnClickListener {
            val numberPickerDialog = NumberPickerDialog(this)
            numberPickerDialog.show()
        }
    }

    fun setButtonText(value: String) {
        binding.btnNumberPicker.text = value
    }
}