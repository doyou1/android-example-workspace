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
import com.example.roomdbsampling.entity.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        // Asset
        binding.btnAsset.setOnClickListener {
            showAssetPopup()
        }

        // Category
        binding.btnCategory.setOnClickListener {
            showCategoryPopup()
        }

        // 登録
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val date = binding.tvDate.text.toString()
        val asset = binding.tvAsset.text.toString()
        val category = binding.tvCategory.text.toString()
        val amount = binding.etAmount.text.toString()
        val memo = binding.etMemo.text.toString()

        if (!isValidate(date, asset, category, amount)) return

//        val item = History(0, date, asset, category, amount.toInt(), memo)

        lifecycleScope.launch(Dispatchers.IO) {
//            (application as BaseApplication).historyDao.insert(item)
            lifecycleScope.launch(Dispatchers.Main) {
//                finish()
            }
        }
    }

    private fun isValidate(date: String, asset: String, category: String, amount: String): Boolean {

        // date validate
        val sdf = SimpleDateFormat("yyyyMMdd")
        sdf.isLenient = false
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            return false
        }

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
                binding.tvDate.text = "$year${monthOfYear + 1}$dayOfMonth"
                Log.e(TAG, "$year${monthOfYear + 1}$dayOfMonth")
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showAssetPopup() {
        val popup = PopupMenu(this, binding.btnAsset)
        popup.setOnMenuItemClickListener { item ->
            Log.e(TAG, "title : ${item.title}")
            binding.tvAsset.text = item.title
            false
        }
        popup.menu.add("현금")
        popup.menu.add("카드")
        popup.menu.add("은행")
        popup.menu.add("기타")
//        popup.menuInflater.inflate(R.menu.menu_select_repeat_and_installment, popup.menu)
        popup.show()
    }

    private fun showCategoryPopup() {
        val popup = PopupMenu(this, binding.btnCategory)
        popup.setOnMenuItemClickListener { item ->
            Log.e(TAG, "title : ${item.title}")
            binding.tvCategory.text = item.title
            false
        }
        popup.menu.add("식비")
        popup.menu.add("경조사")
        popup.menu.add("공과금")
        popup.menu.add("기타")
//        popup.menuInflater.inflate(R.menu.menu_select_repeat_and_installment, popup.menu)
        popup.show()
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}