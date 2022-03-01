package com.example.datepickerproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.datepickerproject.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private val prevTag = "Prev Date Picker"
    private val afterTag = "After Date Picker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 오늘로 기본 날짜 설정
        setDefaultDate()

        // DatePicker를 보여주기 위한 클릭이벤트 설정
        setClickEvent()
    }

    /**
     * 초기화시 기본 날짜 설정
     */
    private fun setDefaultDate() {
        binding.etPrev.setText(getCurrentDate())    // 2022-02-16
        binding.etAfter.setText(getCurrentDate())   // 2022-02-16
    }


    /**
     * 클릭시 DatePicker를 보여주기 위한 이벤트 설정
     */
    private fun setClickEvent() {
        binding.etPrev.setOnClickListener {
            DatePickerFragment(binding.etPrev).show(supportFragmentManager, prevTag)
        }
        binding.etAfter.setOnClickListener {
            DatePickerFragment(binding.etAfter).show(supportFragmentManager, afterTag)
        }
    }

    /**
     * DatePickerFragment로부터 선택한 날짜 데이터를 EditText에 설정
     * 어떤 EditText의 데이터에 대한 날짜 변경인지를 tag 비교로 처리
     */
    fun setDate(formattedDate: String, tag: String?) {
        if (tag == prevTag) {
            binding.etPrev.setText(formattedDate)
        } else if (tag == afterTag) {
            binding.etAfter.setText(formattedDate)
        }
    }

    /**
     * 현재 날짜 데이터 to String
     */
    private fun getCurrentDate() : String {
        val currentDate = LocalDate.now()

        Log.e(TAG, "currentDate: ${currentDate.month.value}, ${currentDate.toString()}")
        //숫자 2는 2월
        return currentDate.toString();  // YYYY-MM-DD
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class DatePickerFragment(val editText: EditText) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val TAG = "DatePickerFragment"
    /**
     * 날짜 선택후 확인, OK 버튼 클릭시 해당 메서드 호출
     */
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Log.e(TAG, "year: $year, month: $month, day: $day")
        /**
         * 0이 1월
         * 1이 2월
         * 2이 3월
         *
         * 9는 10월이므로, 0을 붙여줄 필요가 없다
         * 그래서 조건은 10월인 9보다 작은 경우로 한다
         *
         * 그리고 텍스트로서 보여줄 것이기에 `1`을 더해준다
         */
        // 달, 일이 `10`이하일 경우 앞에 `0`을 붙여주는 로직
        val formattedMonth = if (month < 9) {
            "0${month+1}"
        } else {
            "${month+1}"
        }
        val formattedDay = if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }

        (requireActivity() as MainActivity).setDate("$year-$formattedMonth-$formattedDay", this.tag)
    }

    /**
     * DatePickerFragment().show() 실행시 호출되는 메서드.
     * 현재 선택한 EditText의 데이터를 DatePicker의 최초 날짜값으로 설정
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 2022-02-16
        val date = LocalDate.parse(editText.text.toString(), DateTimeFormatter.ISO_DATE)
        val minusMonthDate = date.minusMonths(1)

        // 2022 -> 2022 / 02 -> 03 / 16 -> 16
        // month는
        /**
         * 0이 1월
         * 1이 2월
         * 2이 3월
         *  ...
         * 개념이기에 보여주고 있는 text를 통해 월(month)을 설정해 DatePicker 객체를 만들때에는
         * `text.month - 1`로 진행을 해야한다.
         */
        //DatePickerDialog에서 숫자 2는 3월
        return DatePickerDialog(requireActivity(), this, minusMonthDate.year, minusMonthDate.month.value, date.dayOfMonth)
    }

}