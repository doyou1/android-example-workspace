package com.example.dialogactivityproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.devspark.appmsg.AppMsg
import com.example.dialogactivityproject.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog)

        setClickEvent()
        setTouchEvent()
    }

    private fun setClickEvent() {

        binding.container.setOnClickListener {
            finish()
        }

        binding.layoutContent.setOnClickListener {
            AppMsg.makeText(this, "layoutContent", AppMsg.STYLE_ALERT).show()
        }
    }

    private fun setTouchEvent() {

    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}