package com.example.appmsgindialogproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.devspark.appmsg.AppMsg

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        setLayoutSize()
        setClickEvent()
    }

    private fun setLayoutSize() {
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        findViewById<ConstraintLayout>(R.id.layout_content).layoutParams.width = width
        findViewById<ConstraintLayout>(R.id.layout_content).layoutParams.height = width
    }

    private fun setClickEvent() {
        findViewById<Button>(R.id.btn_print_toast).setOnClickListener {
            AppMsg.makeText(this, "Toast", AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.black)).show()
        }
    }
}