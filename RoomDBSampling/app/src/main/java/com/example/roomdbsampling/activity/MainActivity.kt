package com.example.roomdbsampling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivityMainBinding
import com.example.roomdbsampling.entity.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list: List<History>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
        setRecyclerView()
    }

    private fun setClickEvent() {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnSummary.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            list = (application as BaseApplication).historyDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
                list?.let {
                    setRecyclerViewLayoutManager()
                    binding.recyclerView.adapter = HistoryRVAdapter(it)
                }
            }
        }
    }

    private fun setRecyclerViewLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }

}