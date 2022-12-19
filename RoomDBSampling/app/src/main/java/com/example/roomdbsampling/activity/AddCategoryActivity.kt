package com.example.roomdbsampling.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbsampling.R
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivityAddAssetBinding
import com.example.roomdbsampling.databinding.ActivityAddCategoryBinding
import com.example.roomdbsampling.databinding.ActivitySummaryBinding
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.dto.Summary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
//        binding.btnDate.setOnClickListener {
//
//        }
    }
}