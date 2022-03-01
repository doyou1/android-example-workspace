package com.example.qnaproject.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.QnaAdapter
import com.example.qnaproject.R
import com.example.qnaproject.databinding.FragmentQnaBinding
import com.example.qnaproject.dialog.QnaRegisterDialog
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.responseModel.QnaResponseModel
import com.example.qnaproject.service.QnaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  ProductActivity 네번째 화면
 */
class FragmentQna : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private var memId:Int = -1  // url param - MEM_ID
    private var page = 1     // url param - Page
    private val TAG = "Fragment4"

    // activity_qna의 Data Binding 객체
    private lateinit var binding: FragmentQnaBinding

    private var list = arrayListOf<Qna>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qna, container, false)

        getSharedPreferenceData()
        drawQnaList()
        setClickEvent()
        return binding.root
    }


    /**
     * GET saved MEM_ID in SharedPreference
     */
    private fun getSharedPreferenceData() {
        val sharedPref = activity?.getSharedPreferences("App", Context.MODE_PRIVATE)
        memId = sharedPref?.getInt("MEM_ID", -1) ?: -1
    }

    private fun drawQnaList() {
        list = ArrayList()
        binding.recyclerView.adapter = QnaAdapter(list)
        page = 1
        setQnaList()
    }

    private fun setClickEvent() {
        binding.toolbarQnaFragment.btnRegister.setOnClickListener {
            QnaRegisterDialog.getInstance(requireContext()).show()
        }
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
        val call: Call<QnaResponseModel> = qnaService.getQnaList(memId, page)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<QnaResponseModel> {
            override fun onResponse(
                call: Call<QnaResponseModel>,
                response: Response<QnaResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as QnaResponseModel
                Log.d(tag, "성공 : ${resBody.data}")
                val newList = resBody.data // ResponseBody 중 Qna List 정보를 담고 있는 객체
                if (newList.size > 0) { // 새로운 List가 있을 시
                    val positionStart = list.size      // 0        10      20      30
                    list.addAll(newList)
                    val itemCount = newList.size          // 10-1     20-1    30-1    34-1

                    // index[positionStart]부터 itemCount개 Refresh
                    binding.recyclerView.adapter?.notifyItemRangeChanged(positionStart,itemCount)
                }
            }

            override fun onFailure(call: Call<QnaResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }
    companion object {
        @Volatile private var instance: FragmentQna? = null

        @JvmStatic fun getInstance(): FragmentQna =
            instance ?: synchronized(this) {
                instance ?: FragmentQna().also {
                    instance = it
                }
            }
    }
}