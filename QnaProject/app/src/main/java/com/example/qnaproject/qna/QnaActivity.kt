package com.example.qnaproject.qna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.R

class QnaActivity : AppCompatActivity() {

    private val qnaList = arrayListOf<Qna>(
        Qna(1, "Title", "2021-02-03"),
        Qna(1, "Title", "2021-02-03"),
        Qna(1, "Title", "2021-02-03"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)
        setSupportActionBar(findViewById(R.id.toolbar))

        val rv_qna = findViewById<RecyclerView>(R.id.rv_qna)
        rv_qna.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_qna.setHasFixedSize(true)
        rv_qna.adapter = QnaAdapter(qnaList)
    }
}