package com.example.fragmentinfragmentsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val frameLayout = findViewById<FrameLayout>(R.id.frame_layout)
        supportFragmentManager.beginTransaction().add(frameLayout.id, SettingFragment.getInstance()).commit()
    }
}