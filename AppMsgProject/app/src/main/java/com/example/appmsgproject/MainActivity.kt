package com.example.appmsgproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.devspark.appmsg.AppMsg
import com.example.appmsgproject.databinding.ActivityMainBinding

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
        binding.btnAppMsg.setOnClickListener {
            val appMsg =
                AppMsg.makeText(this, "App Msg", AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.black))
            appMsg.duration = 500
            appMsg.show()
        }

        binding.btnErrorMsg.setOnClickListener {
            val appMsg =
                AppMsg.makeText(this, "Error Msg", AppMsg.Style(AppMsg.LENGTH_LONG, R.color.alert))
            appMsg.duration = 500
            appMsg.show()
        }

        binding.btnCustomMsg.setOnClickListener {
            val appMsg = AppMsg.makeText(
                this,
                "Custom Msg",
                AppMsg.Style(AppMsg.LENGTH_STICKY, R.color.teal_700)
            )
            appMsg.duration = 500
            appMsg.show()
        }

        binding.btnGravityBottom.setOnClickListener {
            val appMsg = AppMsg.makeText(
                this,
                "Gravity Bottom Msg",
                AppMsg.Style(AppMsg.LENGTH_STICKY, R.color.purple_500)
            )
            appMsg.duration = 500
            appMsg.setLayoutGravity(Gravity.BOTTOM)
            appMsg.show()
        }
    }
}