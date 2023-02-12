package com.example.commutesampling

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.commutesampling.databinding.ActivityDefaultTimeSettingBinding
import com.google.android.material.textfield.TextInputEditText

class DefaultTimeSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefaultTimeSettingBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultTimeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setTimes()
        setDialogAction()
    }

    private fun setClickEvent() {
        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {

        }
    }

    private fun setTimes() {
        val pref = getSharedPreferences(TEXT_SETTING, Context.MODE_PRIVATE)
        var workStart = pref.getString(TEXT_WORK_START_TIME, "09:00")
        var workEnd = pref.getString(TEXT_WORK_END_TIME, "18:00")
        var lunchStart = pref.getString(TEXT_LUNCH_START_TIME, "12:00")
        var lunchEnd = pref.getString(TEXT_LUNCH_END_TIME, "13:00")
        workStart = workStart?.filter { c -> c.toString() != ":" }
        workEnd = workEnd?.filter { c -> c.toString() != ":" }
        lunchStart = lunchStart?.filter { c -> c.toString() != ":" }
        lunchEnd = lunchEnd?.filter { c -> c.toString() != ":" }

        binding.etIn.setText(workStart)
        binding.etLunchStart.setText(lunchStart)
        binding.etLunchEnd.setText(lunchEnd)
        binding.etOut.setText(workEnd)
    }

    private fun setDialogAction() {
        // for action like dialog
        binding.layoutWrap.setOnClickListener {
            finish()
        }
        // for action like dialog
        binding.layoutContent.setOnClickListener {
            Log.d(TAG, "content")

            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}