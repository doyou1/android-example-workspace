package com.example.notificationchannelsampling

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.notificationchannelsampling.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnBasic.setOnClickListener {
            val intent = Intent(this, BasicActivity::class.java)
            startActivity(intent)
        }
        binding.btnMultiple.setOnClickListener {
            val intent = Intent(this, MultipleActivity::class.java)
            startActivity(intent)
        }
    }
}