package com.example.activityforresultproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        setClickEvent()
    }

    private fun setClickEvent() {
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            Log.e("send click", "Hello")
            val intent = Intent()
            intent.putExtra("text", findViewById<EditText>(R.id.editText).text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}