package com.example.qnaproject.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.responseModel.QnaDetailResponseModel
import com.example.qnaproject.service.QnaService
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityQnaDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Qna 상세정보 출력 화면 액티비티
 */
class QnaDetailActivity : AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private val tag = "QnaDetailActivity"

    // activity_qna_detail의 Data Binding 객체
    private lateinit var binding: ActivityQnaDetailBinding

    private var QNA_ID = -1
    private var MEM_ID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_detail)
        setSupportActionBar(binding.toolbarQnaDetail.root as Toolbar)
        setClickEvent()

        // QnaList 화면로부터의 QNA_ID 전송 확인
        QNA_ID = intent.getIntExtra("QNA_ID", -1)
        getSharedPreferenceData()
        if (QNA_ID != -1) setQndDetail(QNA_ID)
        else super.onBackPressed()                       // 적절하지 못한 접근이므로, 이전화면으로 이동
    }

    private fun getSharedPreferenceData() {
        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        MEM_ID = sharedPref.getInt("MEM_ID", -1)

        Log.e(tag, "MEM_ID: ${MEM_ID}")

        if (MEM_ID == -1) {    // 기존의 MEM_ID가 없다면..
            Toast.makeText(this, "적절하지 않은 접근입니다.", Toast.LENGTH_SHORT).show()
            moveToMain()
        }
    }

        /**
     * ClickEvent 설정
     */
    private fun setClickEvent() {
        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaDetail.ibBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    /**
     * 서버로부터 QnaDetail 데이터를 받아오고,
     * draw in UI
     */
    private fun setQndDetail(qnaId: Int) {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val qnaService = retrofit.create(QnaService::class.java)

        // QnaDetail 데이터에 접근하는 call 객체 생성
        // param(QNA_ID)
        val call: Call<QnaDetailResponseModel> = qnaService.getQnaDetail(qnaId)

        // 인터페이스에 QnaDetail 요청
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<QnaDetailResponseModel> {
            // Response Success
            override fun onResponse(call: Call<QnaDetailResponseModel>, response: Response<QnaDetailResponseModel>) {
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as QnaDetailResponseModel
                Log.d(tag, "성공 : ${resBody.data}")
                val qnaDetail = resBody.data[0] // com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 34 path $.data

                Log.e(tag, "qnaDetail.QNA_ANSWER.length: ${qnaDetail.QNA_ANSWER?.length}")
                binding.qnaDetail = qnaDetail
            }

            // Response Fail
            override fun onFailure(call: Call<QnaDetailResponseModel>, t: Throwable) {
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * Activity to Activity 이동, MainActivity(로그인, 회원가입 화면으로 이동)
     */
    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

}



