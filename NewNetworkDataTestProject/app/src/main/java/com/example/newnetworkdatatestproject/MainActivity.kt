package com.example.newnetworkdatatestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.newnetworkdatatestproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "http://211.254.212.85:8080/"
    private val TAG = "MainActivity"
    val categoryCd = "ALL"
    val memId = 60
    var page = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnGetData.setOnClickListener {
            getNetworkData()
        }
    }

    private fun getNetworkData() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val networkDataService = retrofit.create(NetworkDataService::class.java)

        // 구현된 service 객체를 이용해
        // QnaList 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, PAGE)
        val call: Call<EstimationListResponseModel> = networkDataService.getEstimationList(categoryCd, memId, page)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<EstimationListResponseModel> {
            override fun onResponse(
                call: Call<EstimationListResponseModel>,
                response: Response<EstimationListResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as EstimationListResponseModel
                Log.e(TAG, "성공 : ${resBody.data}")
                val newList = resBody.data // ResponseBody 중 Qna List 정보를 담고 있는 객체
            }

            override fun onFailure(call: Call<EstimationListResponseModel>, t: Throwable) {   // Response Fail
                Log.e(TAG, "실패 : $t")
            }
        })
    }

    data class EstimationListResponseModel(val code: Int, val message: String?, val data: ArrayList<Estimation>)
}