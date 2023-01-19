package com.example.datepickerproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DatePickerFragment(private val editText: EditText) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private val TAG = this::class.java.simpleName

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Log.e(TAG, "year: $year, month: $month, day: $day")
        val yyyy = year
        val MM = String.format("%02d", month + 1)
        val dd = String.format("%02d", day)
        (requireActivity() as MainActivity).setDate("$yyyy-$MM-$dd", this.tag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(editText.text.toString())
        return DatePickerDialog(
            requireActivity(),
            this,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }

}