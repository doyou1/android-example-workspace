package com.example.startuponbootupbrsampling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class StartUpBootUpReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(TAG, "onReceive")
        Log.e(TAG, "context: $context")
        Log.e(TAG, "intent: $intent")

//        if(Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            val activityIntent = Intent(context, MainActivity::class.java)
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(activityIntent)
//        }

        val pref = context?.getSharedPreferences("receiver", Context.MODE_PRIVATE)
        pref.edit().putString("receiver",getCurrentDate()).apply()
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return sdf.format(cal.time)
    }
}