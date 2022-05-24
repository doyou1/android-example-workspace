package com.example.notificationchannelsampling

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.notificationchannelsampling.databinding.ActivityMultipleBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MultipleActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName.toString()
    private lateinit var binding: ActivityMultipleBinding
    private lateinit var timePicker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var notificationUtils: NotificationUtils

    //    private lateinit var notificationUtils: NotificationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_multiple)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnPickTime.setOnClickListener {
            showTimePicker()
        }

        binding.btnSetAllAlarm.setOnClickListener {
            setAlarm()
        }
        binding.btnCancelAllAlarm.setOnClickListener {

        }

        binding.btnSet0Alarm.setOnClickListener {

        }

        binding.btnCancel0Alarm.setOnClickListener {

        }

        binding.btnSet30Alarm.setOnClickListener {

        }

        binding.btnCancel30Alarm.setOnClickListener {

        }

        binding.btnSet45Alarm.setOnClickListener {

        }

        binding.btnCancel45Alarm.setOnClickListener {

        }

    }

    private fun showTimePicker() {
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alram Time")
            .build()

        timePicker.show(supportFragmentManager, "jhsampling")

        timePicker.addOnPositiveButtonClickListener {
            if (timePicker.hour > 12) {
                binding.tvPickedTime.text = "${timePicker.hour} : ${(timePicker.minute)} PM"
            } else {
                binding.tvPickedTime.text = "${timePicker.hour} : ${(timePicker.minute)} AM"
            }

            calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        notificationUtils = NotificationUtils(this)

        val intent0 = Intent(this, Alarm0Receiver::class.java)
        val pendingIntent0 = PendingIntent.getBroadcast(this, 0, intent0, PendingIntent.FLAG_CANCEL_CURRENT)
        calendar.set(Calendar.SECOND, 0)
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
        Log.d(TAG, "currenttime0: ${sdf.format(Date(calendar.timeInMillis))}")
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * calendar.getActualMaximum(Calendar.DAY_OF_MONTH), pendingIntent0)

        val intent30 = Intent(this, Alarm30Receiver::class.java)
        val pendingIntent30 = PendingIntent.getBroadcast(this, 0, intent30, PendingIntent.FLAG_CANCEL_CURRENT)
        calendar.set(Calendar.SECOND, 30)
        Log.d(TAG, "currenttime30: ${sdf.format(Date(calendar.timeInMillis))}")
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * calendar.getActualMaximum(Calendar.DAY_OF_MONTH), pendingIntent30)

        val intent45 = Intent(this, Alarm45Receiver::class.java)
        val pendingIntent45 = PendingIntent.getBroadcast(this, 0, intent45, PendingIntent.FLAG_CANCEL_CURRENT)
        calendar.set(Calendar.SECOND, 45)
        Log.d(TAG, "currenttime45: ${sdf.format(Date(calendar.timeInMillis))}")
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * calendar.getActualMaximum(Calendar.DAY_OF_MONTH), pendingIntent45)

        Toast.makeText(this, "Set Alarm Complete", Toast.LENGTH_SHORT).show()

//        2022-05-24 17:25:39.535 14306-14306/com.example.notificationchannelsampling D/MultipleActivity: currenttime0: 5월 24,2022 17:26:00
//        2022-05-24 17:25:39.539 14306-14306/com.example.notificationchannelsampling D/MultipleActivity: currenttime30: 5월 24,2022 17:26:30
//        2022-05-24 17:25:39.543 14306-14306/com.example.notificationchannelsampling D/MultipleActivity: currenttime45: 5월 24,2022 17:26:45

    }
}