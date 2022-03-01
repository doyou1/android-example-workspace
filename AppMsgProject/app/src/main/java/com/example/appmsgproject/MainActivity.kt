package com.example.appmsgproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.devspark.appmsg.AppMsg
import com.example.appmsgproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val list1 = arrayListOf<AppMsg.Style>(AppMsg.STYLE_ALERT,
        AppMsg.STYLE_CONFIRM,
        AppMsg.STYLE_INFO)
//    private val list2 = arrayListOf<Int>(
//        AppMsg.LENGTH_SHORT,
//        AppMsg.LENGTH_LONG,
//        AppMsg.LENGTH_STICKY,
//        AppMsg.PRIORITY_HIGH,
//        AppMsg.PRIORITY_NORMAL,
//        AppMsg.PRIORITY_LOW
//        )

    private val styles = arrayListOf<AppMsg.Style>(
        AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.black),
        AppMsg.Style(AppMsg.LENGTH_LONG, R.color.white),
        AppMsg.Style(AppMsg.LENGTH_STICKY, R.color.purple_700),
    )
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnPrintAppMsg.setOnClickListener {

//            if (list1.size > count1) {
//                AppMsg.makeText(this, "AppMsg", list1[count1++], R.layout.custom_app_msg).show();
//            } else {
//                count1 = 0
//            }
            
            if (styles.size > count) {
                val appMsg = AppMsg.makeText(this, "AppMsg", styles[count++])
                appMsg.duration = 500
                appMsg.show()
            } else {
                count = 0
            }
        }
    }
}