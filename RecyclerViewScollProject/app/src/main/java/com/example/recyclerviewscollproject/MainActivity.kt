package com.example.recyclerviewscollproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.recyclerviewscollproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private val tag = "MainActivity"
    private val MEM_ID = 73
    private var page = 1

    private lateinit var binding: ActivityMainBinding
    private lateinit var qnaAdapter: QnaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setQnaList()
        setRecyclerView()
        setRecyclerViewScollEvent()
    }

    private fun setQnaList() {
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
                val resBody = response.body() as QnaResponseModel
                Log.d(tag, "성공 : ${resBody.data}")
                if (resBody.data != null) {
                    drawRecyclerView(resBody.data)
                }
            }

            override fun onFailure(call: Call<QnaResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    private fun setRecyclerView() {
        qnaAdapter = QnaAdapter()
        binding.rvQna.adapter = qnaAdapter
        binding.rvQna.setHasFixedSize(true)
        binding.rvQna.setOnScrollChangeListener(object: View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if(!binding.rvQna.canScrollVertically(-1)) { // 최상단
                    Log.e(tag, "최상단")
                } else if (!binding.rvQna.canScrollVertically(1)) { // 최하단
                    Log.e(tag, "최하단")
                    page++
                    setQnaList()
                } else {
                    Log.e(tag, "~_~")
                }
            }
        })
    }

    private fun setRecyclerViewScollEvent() {

    }

    private fun drawRecyclerView(newList: MutableList<Qna>) {
        qnaAdapter.list.addAll(newList)
        binding.rvQna.adapter?.notifyDataSetChanged()
    }
}