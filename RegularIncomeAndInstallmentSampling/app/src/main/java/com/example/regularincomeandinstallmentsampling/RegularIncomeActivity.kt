package com.example.regularincomeandinstallmentsampling


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.regularincomeandinstallmentsampling.databinding.ActivityRegularIncomeBinding
import java.text.SimpleDateFormat
import java.time.temporal.TemporalAmount
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
    }

    private fun set() {
        val period = binding.btnPeriod.text.toString()
        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()

        Log.e(TAG, "period: $period")

        if (!isValidate(period, name, amount)) return
        setAlarm(period, name, amount.toInt())
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

    private fun setAlarm(period: String, name: String, amount: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val am = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            intent.putExtra(TEXT_PERIOD, period)
            intent.putExtra(TEXT_NAME, name)
            intent.putExtra(TEXT_AMOUNT, amount)
            intent.putExtra(TEXT_DATE, getCurrentDate())

            val pendingIntent =
                PendingIntent.getBroadcast(this, RESULT_OK, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1 * 1000, pendingIntent)

        } else {
            Toast.makeText(this, "Alarm can use above Marshmallow version", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(cal.time)
    }
}