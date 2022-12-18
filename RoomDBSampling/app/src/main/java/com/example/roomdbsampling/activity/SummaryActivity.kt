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
import com.example.roomdbsampling.databinding.ActivitySummaryBinding
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.dto.Summary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding
    private val TAG = this::class.java.simpleName
    private var histories: List<History>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setRecyclerView(date: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            histories = (application as BaseApplication).historyDao.getByDate(date)
            lifecycleScope.launch(Dispatchers.Main) {
                histories?.let {
                    setRecyclerViewLayoutManager()
                    binding.recyclerView.adapter = HistoryRVAdapter(it)
                }
            }
        }
    }

    private fun setSummary(date: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = (application as BaseApplication).historyDao.getSummaryByDate(date)
            lifecycleScope.launch(Dispatchers.Main) {
                var income = 0
                var consumption = 0
                var transfer = 0
                for (summary in result) {
                    when (summary.type) {
                        // income
                        0 -> {
                            income = summary.result
                        }
                        // consumption
                        1 -> {
                            consumption = summary.result
                        }
                        // transfer
                        2 -> {
                            transfer = summary.result
                        }
                    }
                }

                binding.tvIncome.text = income.toString()
                binding.tvConsumption.text = consumption.toString()
                binding.tvTransfer.text = transfer.toString()
                val sum = income - consumption
                if (sum > 0) {
                    binding.tvSum.text = "+$sum"
                    binding.tvSum.setTextColor(resources.getColor(R.color.orange))
                } else if (sum == 0) {
                    binding.tvSum.text = sum.toString()
                    binding.tvSum.setTextColor(resources.getColor(R.color.black))
                } else {
                    binding.tvSum.text = sum.toString()
                    binding.tvSum.setTextColor(resources.getColor(R.color.blue))
                }
            }
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val date = "$year${monthOfYear + 1}$dayOfMonth"
                binding.tvDate.text = date
                Log.e(TAG, date)
                setRecyclerView(date)
                setSummary(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun setRecyclerViewLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }
}