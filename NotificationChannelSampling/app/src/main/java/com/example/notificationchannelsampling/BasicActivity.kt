package com.example.notificationchannelsampling

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.notificationchannelsampling.databinding.ActivityBasicBinding

@RequiresApi(Build.VERSION_CODES.O)
class BasicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasicBinding
    private lateinit var notificationUtils: NotificationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic)

        notificationUtils = NotificationUtils(this)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSendAndroid.setOnClickListener {
            val title:String = binding.etAndroidTitle.text.toString()
            val author:String = binding.etAndroidAuthor.text.toString()

            if (!title.isNullOrEmpty() && !author.isNullOrEmpty()) {
                val notificationBuilder = notificationUtils.getAndroidChannelNotification(title, "By $author")
                notificationUtils.manager.notify(101, notificationBuilder.build())
            }
        }
        binding.btnSendIos.setOnClickListener {
            val title:String = binding.etIosTitle.text.toString()
            val author:String = binding.etIosAuthor.text.toString()

            if (!title.isNullOrEmpty() && !author.isNullOrEmpty()) {
                val notificationBuilder = notificationUtils.getAndroidChannelNotification(title, "By $author")
                notificationUtils.manager.notify(102, notificationBuilder.build())
            }
        }
        binding.btnAndroidNotiSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationUtils.ANDROID_CHANNEL_ID)
            startActivity(intent)
        }
        binding.btnIosNotiSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationUtils.IOS_CHANNEL_ID)
            startActivity(intent)
        }
    }
}