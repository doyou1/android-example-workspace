package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaDetailBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Qna 상세정보 출력 화면 액티비티
 */
class QnaDetailActivity: AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private var qnaId:Int = -1  // url param - QNA_ID

    private val tag = "QnaDetailActivity"

    // activity_qna_detail의 Data Binding 객체
    private lateinit var binding: ActivityQnaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_detail)
        setSupportActionBar(binding.toolbarQnaDetail.root as Toolbar)

        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaDetail.ibBack.setOnClickListener {
            moveToBack()
        }

        // Get Intent Extra
        if(intent.hasExtra("QNA_ID")) { // QnaList 화면에서 Extra 전송 유무 확인
            qnaId = intent.getIntExtra("QNA_ID", -1)
            if (qnaId != -1) {  // QNA_ID 존재하면
                // Retrofit 객체 생성
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
                    .build()

                // Retrofit 객체로 service 인터페이스 구현
                val qnaService = retrofit.create(QnaService::class.java)

                // QnaDetail 데이터에 접근하는 call 객체 생성
                // param(QNA_ID)
                val call: Call<ResponseModel> = qnaService.getQnaDetail(qnaId)
                CoroutineScope(Dispatchers.IO).launch {
                    async {
                        // 인터페이스에 QnaDetail 요청
                        // return QnaDetail
                        binding.qnaDetail = getInterfaceData(call)
                    }
                }
            } else {    // 존재하지 않으면 초기화면으로 이동
                moveToBack()
            }
        }
    }

    /**
     * QnaDetail 데이터를 요청하는 함수
     */
    suspend fun getInterfaceData(call: Call<ResponseModel>): QnaDetail =
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
                    val data = resBody.data[0].asJsonObject     // ResponseModel의 "data"속성이 JsonArray형태
                                                                // ResponseBody 중 QnaDetail 정보를 담고 있는 객체
                    val qna_title = data.get("QNA_TITLE").asString
                    val qna_content = data.get("QNA_CONTENT").asString
                    val qna_answer = data.get("QNA_ANSWER").asString
                    val qna_con_dt = data.get("QNA_CON_DT").asString
                    val qna_ann_dt = data.get("QNA_ANN_DT").asString

                    val qnaDetail = QnaDetail(qna_title, qna_content, qna_answer, qna_con_dt, qna_ann_dt)


                    continuation.resume(qnaDetail) {    // 얻은 데이터 리턴 | 60: binding.qnaDetail = getInterfaceData(call)
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
     * Activity to Activity 이동, QnaActivity(QnaList화면으로 이동)
     */
    private fun moveToBack() {
        val intent = Intent(this, QnaActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}



