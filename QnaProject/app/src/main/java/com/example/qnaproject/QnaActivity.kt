package com.example.qnaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.*
import kotlinx.coroutines.suspendCancellableCoroutine

import org.json.JSONArray

class QnaActivity : AppCompatActivity() {

    private val url = "https://api.jamjami.co.kr/api/faq/FaqList?MEM_ID=73&PAGE=1"
    private val tag = "QnaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)
        setSupportActionBar(findViewById(R.id.toolbar))

        val queue = Volley.newRequestQueue(this)
        CoroutineScope(Dispatchers.IO).launch {
            async {
                val qnaList = getInterfaceData(queue)
                withContext(Dispatchers.Main) {
                    drawRecyclerView(qnaList)
                }
            }
        }
    }

    suspend fun getInterfaceData(queue: RequestQueue): ArrayList<Qna> =
        suspendCancellableCoroutine { continuation ->
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    val jsonArray: JSONArray = response.getJSONArray("data")
                    val currentList = arrayListOf<Qna>()
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val qna_id = item.getInt("QNA_ID")
                        val qna_title = item.getString("QNA_TITLE")
                        val qna_con_dt = item.getString("QNA_CON_DT")
                        currentList.add(Qna(qna_id, qna_title, qna_con_dt))
                    }
                    continuation.resume(currentList) {
                        Log.e(tag, it.toString())
                    }
                },
                { error: VolleyError? ->
                    println(error?.message)
                }
            )
        queue.add(request)
    }

    private fun drawRecyclerView(qnaList: ArrayList<Qna>) {
        val rv_qna = findViewById<RecyclerView>(R.id.rv_qna)
        rv_qna.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_qna.setHasFixedSize(true)
        rv_qna.adapter = QnaAdapter(qnaList)
    }
}