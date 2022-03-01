package com.example.broadcastreceiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.e("MyReceiver", "intent.action: ${intent.action}")
//        Toast.makeText(context, "전원 연결", Toast.LENGTH_SHORT).show();
        (context as Activity).findViewById<TextView>(R.id.textView).text = intent.action.toString()
    }
}