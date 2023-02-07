package com.example.startforegroundsampling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(Build.VERSION.SDK_INT >= 26) {
            context?.startForegroundService(Intent(context, NotificationService::class.java))
        } else {
            context?.startService(Intent(context, NotificationService::class.java))
        }
    }
}