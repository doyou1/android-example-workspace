package com.example.serviceproject

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var myService:MyService? = null
    var isConService = false
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val b = service as MyService.MyServiceBinder
            myService = b.getService()
            Toast.makeText(applicationContext, "Bind Service", Toast.LENGTH_SHORT).show()
            isConService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConService = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            val text = findViewById<TextView>(R.id.editText).text.toString()

            val intent = Intent(this, MyService::class.java)
            intent.putExtra("command", "service")
            intent.putExtra("text", text)

            startService(intent)
        }

        findViewById<Button>(R.id.btn_bind).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        findViewById<Button>(R.id.btn_check).setOnClickListener {
            Log.e("btn_check", "btn_check")
            if (isConService) {
                myService?.plusCount()
                findViewById<TextView>(R.id.tv_count).text =  myService?.count.toString()
//                Toast.makeText(this, "count: ${myService?.count.toString()}", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btn_close).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
            unbindService(serviceConnection)
            isConService = false
        }

    }


}