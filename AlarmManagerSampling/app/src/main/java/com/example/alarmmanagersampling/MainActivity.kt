package com.example.alarmmanagersampling

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.alarmmanagersampling.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timePicker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()
        init()
        setClickEvent()
    }

    private fun init() {
        val cal = Calendar.getInstance()

        val hour = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))
        val minute = String.format("%02d", cal.get(Calendar.MINUTE))
        val amPm = if (hour.toInt() > 12) {
            "PM"
        } else {
            "AM"
        }
        binding.tvSelectedTime.text = "$hour : $minute $amPm"
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(hour.toInt())
            .setMinute(minute.toInt())
            .setTitleText(TIMEPICKER_TITLE_TEXT)
            .build()
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val reserveSeconds = calendar.get(Calendar.SECOND) + DELAY_SECOND
        calendar.set(year, month, day, hour.toInt(), minute.toInt(), reserveSeconds)
    }

    private fun setClickEvent() {
        binding.btnSelectTime.setOnClickListener {  // show TimePickerDialog
            showTimePicker()
        }
        binding.btnSetAlarm.setOnClickListener {
            setAlarm()
        }
        binding.btnCancleAlarm.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val description = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            notificationChannel.description = description

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun showTimePicker() {
        timePicker.show(supportFragmentManager, NOTIFICATION_CHANNEL_ID)
        timePicker.addOnPositiveButtonClickListener {
            val hour = String.format("%02d", timePicker.hour)
            val minute = String.format("%02d", timePicker.minute)
            val amPm = if (timePicker.hour > 12) {
                "PM"
            } else {
                "AM"
            }
            binding.tvSelectedTime.text = "$hour : $minute $amPm"
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val reserveSeconds = calendar.get(Calendar.SECOND) + DELAY_SECOND
            calendar.set(year, month, day, timePicker.hour, timePicker.minute, reserveSeconds)
        }
    }

    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, TEXT_SUCCESS_SET_ALARM, Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        if (alarmManager == null) {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        }
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, TEXT_SUCCESS_CANCEL_ALARM, Toast.LENGTH_SHORT).show()
    }
}