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
import com.example.roomdbsampling.adapter.HistoryRVAdapter
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivityAddAssetBinding
import com.example.roomdbsampling.databinding.ActivitySummaryBinding
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.dto.Summary
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.util.TEXT_CONSUMPTION
import com.example.roomdbsampling.util.TEXT_INCOME
import com.example.roomdbsampling.util.TEXT_TRANSFER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAssetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetBinding
    private val TAG = this::class.java.simpleName
    private var currentType = -1
    private var list: List<Asset>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetBinding.inflate(layoutInflater)
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
        val amount = binding.etAmount.text.toString()
        val memo = binding.etMemo.text.toString()

        if (!isValidate(type, name, amount)) return

        val asset = Asset(
            0,
            if (type == TEXT_INCOME) 0 else if (type == TEXT_CONSUMPTION) 1 else 2,
            name,
            if (type == TEXT_INCOME) amount.toInt() else -1,
            memo
        )

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BaseApplication).assetDao.insert(asset)
            lifecycleScope.launch(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun setRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            list = (application as BaseApplication).assetDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
                list?.let {
                    setRecyclerViewLayoutManager()
                    binding.recyclerView.adapter = AssetRVAdapter(it)
                }
            }
        }
    }

    private fun showTypePopup() {
        val popup = PopupMenu(this, binding.btnType)
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                TEXT_INCOME -> {
                    currentType = 0
                    binding.etAmount.visibility = View.VISIBLE
                }
                TEXT_CONSUMPTION -> {
                    currentType = 1
                    binding.etAmount.visibility = View.GONE
                }
                TEXT_TRANSFER -> {
                    currentType = 2
                    binding.etAmount.visibility = View.GONE
                }
            }
            binding.tvType.text = item.title

            false
        }
        popup.menu.add(TEXT_INCOME)
        popup.menu.add(TEXT_CONSUMPTION)
        popup.menu.add(TEXT_TRANSFER)
        popup.show()
    }

    private fun isValidate(type: String, name: String, amount: String): Boolean {

        // type validate
        if (!(type == TEXT_INCOME || type == TEXT_CONSUMPTION || type == TEXT_TRANSFER)) return false

        // name validate
        if (name.trim().isNullOrEmpty()) return false

        // amount validate
        try {
            amount.toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    private fun setRecyclerViewLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }
}