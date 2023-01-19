package com.example.dialogactivityproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.devspark.appmsg.AppMsg
import com.example.dialogactivityproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnMoveToDialog.setOnClickListener {
            startActivity(Intent(this, DialogActivity::class.java))
        }
        binding.btnAppMsg.setOnClickListener {
            AppMsg.makeText(this, "AppMsg on Activity", AppMsg.STYLE_INFO).show()
        }
    }
}