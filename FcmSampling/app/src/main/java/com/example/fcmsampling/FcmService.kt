package com.example.fcmsampling

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class FcmService : FirebaseMessagingService() {

    private val TAG = this::class.java.simpleName

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(TAG, "${message.to}")
        Log.e(TAG, "${message.notification?.title}")
        Log.e(TAG, "${message.notification?.body}")

        val pref = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("DATE", getCurrentDate())
        editor.putString("TITLE", message.notification?.title)
        editor.putString("CONTENT", message.notification?.body)
        editor.apply()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e(TAG, "token: $token")
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd/HH:mm:ss")
        return sdf.format(cal.time)
    }
}