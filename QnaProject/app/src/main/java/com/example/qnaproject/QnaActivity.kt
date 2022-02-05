package com.example.qnaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var qnaList = arrayListOf<Qna>()
    private var qnaAdapter = QnaAdapter(qnaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        setSupportActionBar(binding.toolbarQna.root as Toolbar)

        setClickEvent()
        setQndList()
        setRecyclerView()
    }

    /**
     * ClickEvent 설정
     */
    private fun setClickEvent() {
        // 문의등록 화면 이동
        binding.toolbarQna.btnRegister.setOnClickListener {
            moveToRegister()
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
        val call: Call<ResponseModel> = qnaService.getQnaList(memId, page)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) { // Response Success
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

                drawRecyclerView(qnaList)
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {   // Response Fail
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
        qnaAdapter = QnaAdapter(qnaList)
        binding.rvQna.adapter = qnaAdapter
        qnaAdapter.notifyDataSetChanged()   // 새로운 Adapter 설정에 따라 DataSet Refresh
    }

    /**
     * Activity to Activity 이동, QnaRegisterActivity
     */
    private fun moveToRegister() {
        val intent = Intent(this, QnaRegisterActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

}