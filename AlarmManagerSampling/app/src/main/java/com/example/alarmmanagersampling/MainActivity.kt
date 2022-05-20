package com.example.alarmmanagersampling

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        createNotificationChannel()

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
            val name = "MyChannel"
            val description = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel("jhsampling", name, importance)
            notificationChannel.description = description

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
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
                binding.tvSelectedTime.text = "${timePicker.hour} : ${(timePicker.minute)} PM"

//                binding.tvSelectedTime.text = String.format("%02d", "${(timePicker.hour - 12)} : ") + String.format("%02d", "${(timePicker.minute)} PM")
            } else {
                binding.tvSelectedTime.text = "${timePicker.hour} : ${(timePicker.minute)} AM"
            }

            calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * calendar.getActualMaximum(Calendar.DAY_OF_MONTH), pendingIntent)

        Toast.makeText(this, "Set Alarm Success", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        if (alarmManager == null) {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        }

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this, "Cancel Alarm Success", Toast.LENGTH_SHORT).show()
    }
}