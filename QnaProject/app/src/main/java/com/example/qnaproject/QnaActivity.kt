package com.example.qnaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnaproject.databinding.ActivityQnaBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Qna List 출력 화면 액티비티
 */
class QnaActivity : AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private val memId = 73  // url param - MEM_ID
    private val page = 1     // url param - Page
    private val tag = "QnaActivity"

    // activity_qna의 Data Binding 객체
    private lateinit var binding: ActivityQnaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_qna) // use DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        setSupportActionBar(binding.toolbarQna.root as Toolbar)

        // Retrofit 객체 생성
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val qnaService = retrofit.create(QnaService::class.java)

        // 구현된 service 객체를 이용해
        // QnaList 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, PAGE)
        val call: Call<ResponseModel> = qnaService.getQnaList(memId, page)
        CoroutineScope(Dispatchers.IO).launch {
            async {

                // 인터페이스에 QnaList 요청
                // return ArrayList<Qna>
                val qnaList = getInterfaceData(call)

                // 인터페이스 요청이 종료된 이후,
                // use QnaList to draw RecyclerView
                withContext(Dispatchers.Main) { // 병행처리를 위해 다른 쓰레드 활용, View 생성 작업은 Dispatchers.Main에서만
                    drawRecyclerView(qnaList)
                }
            }
        }
    }

    /**
     * QnaList 데이터를 요청하는 함수
     */
    suspend fun getInterfaceData(call: Call<ResponseModel>): ArrayList<Qna> =
        suspendCancellableCoroutine { continuation ->
            // 선언한 call 객체에 queue 추가
            call.enqueue(object : Callback<ResponseModel> {
                // Response Success
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                    val resBody = response.body() as ResponseModel
                    Log.d(tag, "성공 : ${resBody.data}")
                    val data = resBody.data // ResponseBody 중 Qna List 정보를 담고 있는 객체
                    val currentList = arrayListOf<Qna>()
                    for (i in 0 until data.size()) {
                        val item = data.get(i).asJsonObject // data.get(index): JsonArray

                        val qna_id = item.get("QNA_ID").asInt // JsonArray.get("name"): JsonPrimitive
                        val qna_title = item.get("QNA_TITLE").asString
                        val qna_con_dt = item.get("QNA_CON_DT").asString
                        currentList.add(Qna(qna_id, qna_title, qna_con_dt))
                    }

                    // 비동기작업 수행을 위해 멈춰둔 쓰레드 데이터 보내주며 재시작
                    continuation.resume(currentList) {
                        Log.e(tag, it.toString())
                    }
                }
                // Response Fail
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.d(tag, "실패 : $t")
                }
            })
    }

    /**
     * 인터페이스로부터 받아온 QnaList로 RecyclerView를 그리는 함수
     */
    private fun drawRecyclerView(qnaList: ArrayList<Qna>) {
        binding.rvQna.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvQna.setHasFixedSize(true)
        binding.rvQna.adapter = QnaAdapter(qnaList)
    }
}