package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

class QnaDetailActivity: AppCompatActivity() {

    private val url = "https://api.jamjami.co.kr/api/faq/FaqInfo"
    private val tag = "QnaDetailActivity"

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
            if (qna_id != -1) {
                val urlWithQnaId = url.plus("?QNA_ID=${qna_id}")
                val queue = Volley.newRequestQueue(this)
                CoroutineScope(Dispatchers.IO).launch {
                    async {
                        val qnaDetail = getInterfaceData(queue, urlWithQnaId)
                        withContext(Dispatchers.Main) {
                            drawUI(qnaDetail)
                        }
                    }
                }
            }
        }
    }

    suspend fun getInterfaceData(queue: RequestQueue, urlWithQnaId:String): QnaDetail =
        suspendCancellableCoroutine { continuation ->
            val request = JsonObjectRequest(
                Request.Method.GET, urlWithQnaId, null,
                { response ->
                    val data:JSONObject = response.getJSONArray("data")[0] as JSONObject

                    val qna_title = data.getString("QNA_TITLE")
                    val qna_content = data.getString("QNA_CONTENT")
                    val qna_answer = data.getString("QNA_ANSWER")
                    val qna_con_dt = data.getString("QNA_CON_DT")
                    val qna_ann_dt = data.getString("QNA_ANN_DT")

                    val qnaDetail = QnaDetail(qna_title, qna_content, qna_answer, qna_con_dt, qna_ann_dt)

                    continuation.resume(qnaDetail) {
                        Log.e(tag, it.toString())
                    }
                },
                { error: VolleyError? ->
                    println(error?.message)
                }
            )
        queue.add(request)
    }

    private fun drawUI(qnaDetail: QnaDetail) {
        findViewById<TextView>(R.id.qna_title).text = qnaDetail.QNA_TITLE
        findViewById<TextView>(R.id.qna_con_dt).text = qnaDetail.QNA_CON_DT
        findViewById<TextView>(R.id.qna_content).text = qnaDetail.QNA_CONTENT

        if (qnaDetail.QNA_ANN_DT != "null") {
            findViewById<TextView>(R.id.qna_answer).text = qnaDetail.QNA_ANSWER
            findViewById<TextView>(R.id.qna_ann_dt).text = qnaDetail.QNA_ANN_DT
        } else {
            findViewById<ConstraintLayout>(R.id.layout_qna_answer).visibility = View.GONE
            findViewById<View>(R.id.line).visibility = View.GONE
        }
    }

}



