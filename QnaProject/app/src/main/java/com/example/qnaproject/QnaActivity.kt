package com.example.qnaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        rv_qna.setOnClickListener(View.OnClickListener {  })

    }
}