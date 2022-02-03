package com.example.qnaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class QnaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}