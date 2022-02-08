package com.example.qnaproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.databinding.FragmentHomeBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.responseModel.ProductResponseModel
import com.example.qnaproject.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding:FragmentHomeBinding

    private val MEM_ID = 94
    private val ITM_ONLY_VIEW = "N"
    private var PAGE = 1

    private var productList = arrayListOf<Product>()
    private lateinit var productAdapter:ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = ProductAdapter(productList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setRecyclerView()
        setProductList()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.rvProduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
        setRecyclerViewScrollEvent()
    }

    /**
     * RecyclerView의 최하단(Bottom) 스크롤을 인지하는 Listener 등록
     */
    private fun setRecyclerViewScrollEvent() {
        binding.rvProduct.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition();
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1);
                if (lastVisibleItemPosition == itemTotalCount) {
                    Log.d(tag, "last Position...");
                    PAGE++  // 다음 페이지 호출을 위한 page index 변경
                    setProductList()
                }
            }
        })
    }

    private fun setProductList() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(ProductService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, ITM_ONLY_VIEW, PAGE)
        val call: Call<ProductResponseModel> = productService.getProductList(MEM_ID, ITM_ONLY_VIEW, PAGE)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as ProductResponseModel
                val newList = resBody.data
                if (newList.size == 0) PAGE--   // productList.size == 0 일경우, 해당 페이지 인덱스에 List가 없음을 의미

                drawRecyclerView(newList)
            }

            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    private fun drawRecyclerView(newList: ArrayList<Product>) {
        if (newList.size > 0) {
            val positionStart = productList.size    // 0        10      20      30
            productList.addAll(newList)
            val itemCount = newList.size            // 10-1     20-1    30-1    34-1
            productAdapter.notifyItemRangeChanged(positionStart,itemCount)
        }
    }

    // Java의 static과 비슷, 추후 공부 필요
    companion object {
        @Volatile private var instance: HomeFragment? = null

        @JvmStatic fun getInstance(): HomeFragment =
            instance ?: synchronized(this) {
                instance ?: HomeFragment().also {
                    instance = it
                }
            }
    }
}