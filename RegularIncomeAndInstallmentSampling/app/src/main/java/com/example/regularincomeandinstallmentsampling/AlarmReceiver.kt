package com.example.regularincomeandinstallmentsampling


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {

        val period = intent?.getStringExtra(TEXT_PERIOD)
        val name = intent?.getStringExtra(TEXT_NAME)
        val amount = intent?.getIntExtra(TEXT_AMOUNT, -1)
        val date = intent?.getStringExtra(TEXT_DATE)
        val nextDate = getNextDate(period, date)

        Log.e(TAG, "period: $period, name: $name, amount: $amount, date: $date nextDate: $nextDate")



//        val am = context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
//        val nextIntent = Intent(context, AlarmReceiver::class.java)
//        nextIntent.putExtra(com.example.regularincomeandinstallmentsampling.TEXT_PERIOD, period)
//        nextIntent.putExtra(com.example.regularincomeandinstallmentsampling.TEXT_NAME, name)
//        nextIntent.putExtra(com.example.regularincomeandinstallmentsampling.TEXT_AMOUNT, amount)
//        nextIntent.putExtra(com.example.regularincomeandinstallmentsampling.TEXT_DATE, nextDate)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            AppCompatActivity.RESULT_OK, nextIntent, PendingIntent.FLAG_IMMUTABLE
//        )
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000, pendingIntent)
    }

    private fun getNextDate(period: String?, date: String?): String {
        val cal = Calendar.getInstance()

        return when (period) {
            // tomorrow
            TEXT_EVERY_DAY -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() - 1,
                    date?.substring(6, 8)!!.toInt() + 1
                )
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // next week
            TEXT_EVERY_WEEK -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() - 1,
                    date?.substring(6, 8)!!.toInt() + 7
                )
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // two weeks ago
            TEXT_EVERY_TWO_WEEKS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() - 1,
                    date?.substring(6, 8)!!.toInt() + 14
                )
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // four weeks ago
            TEXT_EVERY_FOUR_WEEKS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() - 1,
                    date?.substring(6, 8)!!.toInt() + 28
                )
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // next month
            TEXT_EVERY_MONTH -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt(),
                    date?.substring(6, 8)!!.toInt()
                )
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // last day of next month
            TEXT_THE_END_OF_THE_MONTH -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt(),
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // two months ago
            TEXT_EVERY_TWO_MONTHS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() + 1,
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            // three months ago
            TEXT_EVERY_THREE_MONTHS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() + 2,
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            TEXT_EVERY_FOUR_MONTHS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() + 3,
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            TEXT_EVERY_SIX_MONTHS -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt(),
                    date?.substring(4, 6)!!.toInt() + 5,
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            TEXT_EVERY_YEAR -> {
                cal.set(
                    date?.substring(0, 4)!!.toInt() + 1,
                    date?.substring(4, 6)!!.toInt() - 1,
                    date?.substring(6, 8)!!.toInt()
                )
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sdf = SimpleDateFormat("yyyyMMdd")
                sdf.format(cal.time)
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }
}