package com.example.regularincomeandinstallmentsampling.regularincome


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.regularincomeandinstallmentsampling.RepeatDialog
import com.example.regularincomeandinstallmentsampling.databinding.ActivityRegularIncomeBinding
import com.example.regularincomeandinstallmentsampling.room.BaseApplication
import com.example.regularincomeandinstallmentsampling.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegularIncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegularIncomeBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegularIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnPeriod.text = REPEAT_ITEM_LIST[(0..10).random()]
        binding.btnDate.text = getCurrentDate()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnPeriod.setOnClickListener {
            RepeatDialog().show(supportFragmentManager, TEXT_REPEAT)
        }

        binding.btnSet.setOnClickListener {
            set()
        }
    }

    fun periodItemClick(item: String) {
        binding.btnPeriod.text = item

        if (item == TEXT_THE_END_OF_THE_MONTH) {
            val yearMonth = binding.btnDate.text.toString().substring(0, 6)
            binding.btnDate.text = Util.getLatestDate(yearMonth)
        }
    }

    private fun set() {
        val period = binding.btnPeriod.text.toString()
        val date = binding.btnDate.text.toString()
        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()

        if (!isValidate(period, name, amount)) return

        val item = RegularIncome(0, Util.getPeriod(period), date, name, amount.toInt(), true)

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BaseApplication).regularIncomeDao.insert(item)
            lifecycleScope.launch(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun isValidate(period: String, name: String, amount: String): Boolean {

        // validate period
        if (period.isNullOrEmpty() || period == TEXT_SHOW_PERIOD) return false

        // validate name
        if (name.isNullOrEmpty()) return false

        // validate amount
        if (amount.isNullOrEmpty()) return false
        try {
            amount.toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(cal.time)
    }
}