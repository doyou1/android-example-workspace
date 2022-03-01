package com.example.checkboxproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkboxproject.databinding.ActivityMainBinding
import com.example.checkboxproject.databinding.ListCodeItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private val baseUrl = "http://211.254.212.85:8080/"
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var list = arrayListOf<Code>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 리사이클러뷰 초기화
        setRecyclerView()
        // 버튼 클릭 이벤트 추가
//        setClickEvent()
        // SwipeRefreshLayout 스크롤 이벤트 추가
        setRefreshEvent()
        // 인터페이스 url 접근 및 데이터 셋팅
        addCodeList()
    }

    /**
     * 리사이클러뷰 초기화
     */
    private fun setRecyclerView() {
        Log.e(TAG, "setRecyclerView")
        binding.recyclerView.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager(applicationContext).orientation,
            false
        )
        // 액티비티 아이템을 adapter에서 접근하기 위해 바인딩 객체 파라미터로 접근
        binding.recyclerView.adapter = CodeAdapter(list, ::setResult)
        // 아이템끼리의 구분선 추가
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager(applicationContext).orientation
            )
        )
    }

    /**
     * SwipeRefreshLayout 스크롤 이벤트 설정
     * 이벤트 발생시 Code List 초기화 및 인터페이스 url 호출 및 데이터 셋팅
     */
    private fun setRefreshEvent() {
        binding.pullToRefresh.setOnRefreshListener {
            list.clear()
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.tvResult.text = "결과"
            addCodeList()

            // 없으면 새로고침 애니메이션 끝나지 않음
            binding.pullToRefresh.isRefreshing = false
        }
    }

    /**
     * 인터페이스 url 호출 및 데이터 셋팅 메서드
     */
    private fun addCodeList() {
        Log.e(TAG, "addCodeList")
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val networkDataService = retrofit.create(NetworkDataService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<CodeListResponseModel> = networkDataService.getCodeList()
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<CodeListResponseModel> {
            override fun onResponse(
                call: Call<CodeListResponseModel>,
                response: Response<CodeListResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as CodeListResponseModel
                Log.e(TAG, resBody.toString())
                val newList = resBody.data
                Log.e(TAG, newList.toString())

                if (newList.size > 0) { // 서버로부터의 데이터가 있다면
                    val positionStart = list.size   // notifyItemRangeChanged 시작 지점
                    val itemCount = newList.size    // 새로운 item의 수
                    list.addAll(newList)
                    binding.recyclerView.adapter?.notifyItemRangeChanged(positionStart, itemCount)
                }
            }

            override fun onFailure(
                call: Call<CodeListResponseModel>,
                t: Throwable
            ) {   // Response Fail
                Log.d(TAG, "실패 : $t")
            }
        })
    }

    /**
     * 체크박스 `checked`에 따른 텍스트뷰 텍스트 변화 메서드
     */
    private fun setResult() {
        Log.e(TAG, "setResult")
        var result = ""
        list.forEach { code ->
            if (code.checked == true) {
                result += "${code.CD_NAME}/"
            }
        }
        // ${CD_NAME}/${CD_NAME}/${CD_NAME}/ 마지막에 남는 `/` 제거를 위한 코드
        if (result.isNotEmpty()) {
            result = result.subSequence(0, result.length - 1).toString()
            binding.tvResult.text = result
        } else {
            binding.tvResult.text = "결과"
        }
    }
}

/**
 * 코드리스트 리사이클러뷰 어댑터
 */
class CodeAdapter(val list: ArrayList<Code>, private val setResult: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "CodeAdapter"

    // list_code_item.xml 데이터 바인딩 객체
    private lateinit var mBinding: ListCodeItemBinding

    // CreateViewHolder & databinding
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_code_item,
            viewGroup,
            false
        )

        Log.e(TAG, "onCreateViewHolder")
        return CodeViewHolder(mBinding)
    }

    // 아이템별 리스트 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CodeViewHolder) {
            Log.e(TAG, "onBindViewHolder")

            val code: Code = list[position]
            holder.bind(code, setResult)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CodeViewHolder(private val mBinding: ListCodeItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        // 아이템뷰별 리스트 데이터 셋팅 메서드
        fun bind(code: Code, setResult: () -> Unit) {
            Log.e(TAG, "bind")

            // CD Name이 있는 경우, 체크박스 checked true로 처리
//            if (code.CD_NAME.isNotEmpty()) {
//                code.checked = true
//            }

            mBinding.code = code

            // checkbox 클릭시
            mBinding.cbCdName.setOnCheckedChangeListener { compoundButton, isChecked ->
                Log.e(TAG, "isChecked: $isChecked")
                code.checked = isChecked
                setResult()
            }
        }


    }
}

interface NetworkDataService {
    @GET("api/code/CodeListNew")
    fun getCodeList(): Call<CodeListResponseModel>
}

data class Code(val CD_CODE: String, val CD_NAME: String, var checked: Boolean? = true)
data class CodeListResponseModel(val code: Int, val message: String?, val data: ArrayList<Code>)

