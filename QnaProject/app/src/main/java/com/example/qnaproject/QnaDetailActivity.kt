package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QnaDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val mActivity = this as QnaDetailActivity
        findViewById<ImageButton>(R.id.tb_back).setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, QnaActivity::class.java)

                mActivity.startActivity(intent)
                mActivity.finish()
            }
        )


        // Get Intent Extra
        if(intent.hasExtra("QNA_ID")) {
            val qna_id = intent.getIntExtra("QNA_ID", -1)
//            findViewById<TextView>(R.id.tv_test).text = qna_id.toString()
        }
    }
}



