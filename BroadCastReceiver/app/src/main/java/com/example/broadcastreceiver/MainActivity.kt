package com.example.broadcastreceiver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var mReceiver: MyReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            mReceiver = MyReceiver()// 필터를 정의하여 MyReceiver 전송

            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_POWER_CONNECTED)
            filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
            registerReceiver(mReceiver, filter)

            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btn_unregister).setOnClickListener {
            unregisterReceiver(mReceiver)
            Toast.makeText(this, "UnRegister", Toast.LENGTH_SHORT).show()
        }
    }
}