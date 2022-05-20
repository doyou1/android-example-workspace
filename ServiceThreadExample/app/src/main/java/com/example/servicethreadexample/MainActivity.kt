package com.example.servicethreadexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<Button>(R.id.btn_start);
        val btnEnd = findViewById<Button>(R.id.btn_end);

        btnStart.setOnClickListener {
            Toast.makeText(this, "Service 시작", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Time5Service::class.java)
            startService(intent)
        }

        btnEnd.setOnClickListener {
            Toast.makeText(this, "Service 끝", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Time5Service::class.java)
            stopService(intent)
        }

    }
}