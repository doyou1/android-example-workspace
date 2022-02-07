package com.example.qnaproject.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnaproject.*
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.responseModel.QnaResponseModel
import com.example.qnaproject.service.QnaService
import com.example.qnaproject.databinding.ActivityQnaBinding
import com.kakao.sdk.user.UserApiClient
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
    private var MEM_ID:Int = -1  // url param - MEM_ID
    private val page = 1     // url param - Page
    private val tag = "QnaActivity"

    // activity_qna의 Data Binding 객체
    private lateinit var binding: ActivityQnaBinding

    private var qnaList = arrayListOf<Qna>()
    private var qnaAdapter = QnaAdapter(qnaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        setSupportActionBar(binding.toolbarQna.root as Toolbar)
        getSharedPreferenceData()
        setClickEvent()
        setQndList()
        setRecyclerView()
    }

    private fun getSharedPreferenceData() {
        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        MEM_ID = sharedPref.getInt("MEM_ID", -1)

        Log.e(tag, "MEM_ID: ${MEM_ID}")

        if(MEM_ID == -1) {    // 기존의 MEM_ID가 없다면..
            Toast.makeText(this, "적절하지 않은 접근입니다.", Toast.LENGTH_SHORT).show()
            moveToMain()
        }
    }

    /**
     * ClickEvent 설정
     */
    private fun setClickEvent() {
        // 문의등록 화면 이동
        binding.toolbarQna.btnRegister.setOnClickListener {
            moveToRegister()
        }
        binding.toolbarQna.btnKakaoLogout.setOnClickListener{
            logout()
        }
    }

    /**
     * 서버로부터 QnaList 데이터를 얻고,
     * RecyclerView refresh
     */
    private fun setQndList() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val qnaService = retrofit.create(QnaService::class.java)

        // 구현된 service 객체를 이용해
        // QnaList 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, PAGE)
        val call: Call<QnaResponseModel> = qnaService.getQnaList(MEM_ID, page)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<QnaResponseModel> {
            override fun onResponse(
                call: Call<QnaResponseModel>,
                response: Response<QnaResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as QnaResponseModel
                Log.d(tag, "성공 : ${resBody.data}")
                qnaList = resBody.data // ResponseBody 중 Qna List 정보를 담고 있는 객체
                drawRecyclerView(qnaList)
            }

            override fun onFailure(call: Call<QnaResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * RecyclerView 최초 생성
     * 이후 QnaList 데이터 추가시, refresh될 예정
     */
    private fun setRecyclerView() {
        binding.rvQna.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvQna.setHasFixedSize(true)
        binding.rvQna.adapter = qnaAdapter
    }

    /**
     * 인터페이스로부터 받아온 QnaList로 RecyclerView를 그리는 함수
     */
    private fun drawRecyclerView(qnaList: ArrayList<Qna>) {
        qnaAdapter.list = qnaList.toMutableList()
        qnaAdapter.notifyDataSetChanged()   // 새로운 Adapter 설정에 따라 DataSet Refresh
    }

    private fun logout() {
        // 저장된 MEM_ID 삭제
        this.getSharedPreferences("App", Context.MODE_PRIVATE).edit().clear().commit();
        moveToMain()
    }
    /**
     * Activity to Activity 이동, QnaRegisterActivity
     */
    private fun moveToRegister() {
        val intent = Intent(this, QnaRegisterActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

}