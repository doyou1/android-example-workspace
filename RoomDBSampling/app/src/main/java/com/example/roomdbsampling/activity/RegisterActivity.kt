package com.example.roomdbsampling.activity

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.roomdbsampling.application.BaseApplication
import com.example.roomdbsampling.databinding.ActivityRegisterBinding
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val TAG = this::class.java.simpleName

    private var currentType = -1
    private var currentAssets: List<Asset>? = null
    private var currentCategories: List<Category>? = null
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.layoutWrap.setOnClickListener {
            hideKeyboard()
        }

        // Date
        binding.btnDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnType.setOnClickListener {
            showTypePopup()
        }

        binding.btnRepeat.setOnClickListener {
            showRepeatPopup()
        }

        // Asset
        binding.btnAsset.setOnClickListener {
            if (currentType != -1) showAssetPopup()
        }

        // Category
        binding.btnCategory.setOnClickListener {
            if (currentType != -1) showCategoryPopup()
        }

        // 登録
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val date = binding.tvDate.text.toString() + getCurrentHHmm()
        val repeat = getRepeatByButtonText(binding.btnRepeat.text.toString())
        val asset = binding.tvAsset.text.toString()
        val category = binding.tvCategory.text.toString()
        val amount = binding.etAmount.text.toString()
        val memo = binding.etMemo.text.toString()

        if (!isValidate(date, repeat, asset, category, amount)) return

        val assetId = getAssetId(asset)
        val categoryId = getCategoryId(category)

        val item = History(
            0,
            currentType,
            repeat,
            date,
            assetId,
            asset,
            categoryId,
            category,
            amount.toInt(),
            memo
        )

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BaseApplication).historyDao.insert(item)
            lifecycleScope.launch(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun isValidate(
        date: String,
        repeat: Int,
        asset: String,
        category: String,
        amount: String
    ): Boolean {
        // date validate
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        sdf.isLenient = false
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            return false
        }
        // repeat
        if (!(-1 <= repeat && repeat <= 10)) return false
        // asset validate
        if (asset.trim().isNullOrEmpty()) return false
        // category validate
        if (category.trim().isNullOrEmpty()) return false
        // amount validate
        try {
            amount.toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->

                val yyyy = year
                val MM = String.format("%02d", monthOfYear + 1)
                val dd = String.format("%02d", dayOfMonth)

                binding.tvDate.text = "$yyyy$MM$dd"
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTypePopup() {
        val popup = PopupMenu(this, binding.btnType)
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                TEXT_INCOME -> {
                    currentType = 0
                }
                TEXT_CONSUMPTION -> {
                    currentType = 1
                }
                TEXT_TRANSFER -> {
                    currentType = 2
                }
            }
            binding.tvType.text = item.title
            binding.tvAsset.text = null
            binding.tvCategory.text = null

            false
        }

        popup.menu.add(TEXT_INCOME)
        popup.menu.add(TEXT_CONSUMPTION)
        popup.menu.add(TEXT_TRANSFER)
        popup.show()
    }

    private fun showRepeatPopup() {
        val popup = PopupMenu(this, binding.btnRepeat)
        popup.setOnMenuItemClickListener { item ->
            binding.btnRepeat.text = item.title
            binding.tvRepeat.text = item.title
            false
        }

        popup.menu.add(TEXT_NONE)
        popup.menu.add(TEXT_EVERY_1_MINUTES)
        popup.menu.add(TEXT_EVERY_3_MINUTES)
        popup.menu.add(TEXT_EVERY_5_MINUTES)
        popup.menu.add(TEXT_EVERY_10_MINUTES)
        popup.menu.add(TEXT_EVERY_15_MINUTES)
        popup.menu.add(TEXT_EVERY_30_MINUTES)
        popup.menu.add(TEXT_EVERY_45_MINUTES)
        popup.menu.add(TEXT_EVERY_1_HOURS)
        popup.menu.add(TEXT_EVERY_2_HOURS)
        popup.menu.add(TEXT_EVERY_6_HOURS)
        popup.menu.add(TEXT_EVERY_12_HOURS)
        popup.show()
    }

    private fun getRepeatByButtonText(text: String): Int {
        return when (text) {
            TEXT_NONE -> FLAG_NONE
            TEXT_EVERY_1_MINUTES -> FLAG_EVERY_1_MINUTES
            TEXT_EVERY_3_MINUTES -> FLAG_EVERY_3_MINUTES
            TEXT_EVERY_5_MINUTES -> FLAG_EVERY_5_MINUTES
            TEXT_EVERY_10_MINUTES -> FLAG_EVERY_10_MINUTES
            TEXT_EVERY_15_MINUTES -> FLAG_EVERY_15_MINUTES
            TEXT_EVERY_30_MINUTES -> FLAG_EVERY_30_MINUTES
            TEXT_EVERY_45_MINUTES -> FLAG_EVERY_45_MINUTES
            TEXT_EVERY_1_HOURS -> FLAG_EVERY_1_HOURS
            TEXT_EVERY_2_HOURS -> FLAG_EVERY_2_HOURS
            TEXT_EVERY_6_HOURS -> FLAG_EVERY_6_HOURS
            TEXT_EVERY_12_HOURS -> FLAG_EVERY_12_HOURS
            else -> throw NotImplementedError()
        }
    }

    private fun showAssetPopup() {
        lifecycleScope.launch(Dispatchers.IO) {
            currentAssets = (application as BaseApplication).assetDao.getByType(currentType)
            lifecycleScope.launch(Dispatchers.Main) {
                currentAssets?.let {
                    val popup = PopupMenu(mContext, binding.btnAsset)
                    popup.setOnMenuItemClickListener { item ->
                        binding.tvAsset.text = item.title
                        false
                    }
                    for (asset in it) {
                        popup.menu.add(asset.name)
                    }
                    popup.show()
                }
            }
        }
    }

    private fun showCategoryPopup() {
        lifecycleScope.launch(Dispatchers.IO) {
            currentCategories = (application as BaseApplication).categoryDao.getByType(currentType)
            lifecycleScope.launch(Dispatchers.Main) {
                currentCategories?.let {
                    val popup = PopupMenu(mContext, binding.btnCategory)
                    popup.setOnMenuItemClickListener { item ->
                        binding.tvCategory.text = item.title
                        false
                    }
                    for (category in it) {
                        popup.menu.add(category.name)
                    }
                    popup.show()
                }

            }
        }
    }

    private fun getAssetId(name: String): Int {
        currentAssets?.let {
            return it.filter { item -> item.name == name }[0].id
        }
        return -1
    }

    private fun getCategoryId(name: String): Int {
        currentCategories?.let {
            return it.filter { item -> item.name == name }[0].id
        }
        return -1
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

    private fun getCurrentHHmm(): String {
        val cal = Calendar.getInstance()


        // date validate
        val sdf = SimpleDateFormat("HHmm")
        Log.e(TAG, "sdf.format(cal.time): ${sdf.format(cal.time)}")

        return sdf.format(cal.time)
    }
}