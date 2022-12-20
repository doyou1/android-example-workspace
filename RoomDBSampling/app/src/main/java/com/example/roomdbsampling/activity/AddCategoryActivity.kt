package com.example.roomdbsampling.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbsampling.R
import com.example.roomdbsampling.adapter.AssetRVAdapter
import com.example.roomdbsampling.adapter.CategoryRVAdapter
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivityAddAssetBinding
import com.example.roomdbsampling.databinding.ActivityAddCategoryBinding
import com.example.roomdbsampling.databinding.ActivitySummaryBinding
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.dto.Summary
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    private val TAG = this::class.java.simpleName
    private var currentType = -1
    private var list: List<Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
        setRecyclerView()

    }

    private fun setClickEvent() {
        binding.btnType.setOnClickListener {
            showTypePopup()
        }

        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val type = binding.tvType.text.toString()
        val name = binding.etName.text.toString()
        val memo = binding.etMemo.text.toString()

        if (!isValidate(type, name)) return

        val category = Category(
            0,
            if (type == Const.TEXT_INCOME) 0 else if (type == Const.TEXT_CONSUMPTION) 1 else 2,
            name,
            memo
        )

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BaseApplication).categoryDao.insert(category)
            lifecycleScope.launch(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun setRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            list = (application as BaseApplication).categoryDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
                list?.let {
                    setRecyclerViewLayoutManager()
                    binding.recyclerView.adapter = CategoryRVAdapter(it)
                }
            }
        }
    }

    private fun showTypePopup() {
        val popup = PopupMenu(this, binding.btnType)
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                Const.TEXT_INCOME -> {
                    currentType = 0
                }
                Const.TEXT_CONSUMPTION -> {
                    currentType = 1
                }
                Const.TEXT_TRANSFER -> {
                    currentType = 2
                }
            }
            binding.tvType.text = item.title

            false
        }
        popup.menu.add(Const.TEXT_INCOME)
        popup.menu.add(Const.TEXT_CONSUMPTION)
        popup.menu.add(Const.TEXT_TRANSFER)
        popup.show()
    }

    private fun isValidate(type: String, name: String): Boolean {

        // type validate
        if (!(type == Const.TEXT_INCOME || type == Const.TEXT_CONSUMPTION || type == Const.TEXT_TRANSFER)) return false

        // name validate
        if (name.trim().isNullOrEmpty()) return false

        return true
    }

    private fun setRecyclerViewLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }
}