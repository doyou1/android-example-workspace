package com.example.qnaproject.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentHomeBinding
import com.example.qnaproject.databinding.FragmentUpdateBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.responseModel.ProductResponseModel
import com.example.qnaproject.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  ProductActivity 세번째 화면
 */
class UpdateFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mContext: Context
    private lateinit var backButtonCallback: OnBackPressedCallback  // 추가할 backbutton callback 함수
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private var ITM_ID: Int = -1    // Product Item ID

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        if (arguments?.getInt("ITM_ID") == null) {  // ITM_ID가 없다면, 잘못된 접근
            val parentActivity = activity as ProductActivity
            parentActivity.changeFragment("home", null) // HomeFragment로 이동
        } else {
            ITM_ID = arguments?.getInt("ITM_ID")!!  // 다른 형태 고민 해볼 것
            Log.e(tag, "itm_id: ${ITM_ID}")

            galleryLauncher = getGalleryActivityResultLauncher()
            setProductData()
            setClickEvent()
        }
        return binding.root
    }

    /**
     * 레이아웃 아이템 클릭 이벤트
     */
    private fun setClickEvent() {
        binding.toolbarProductUpdate.tbBack.setOnClickListener {  // 툴바 '<'(BackButton) 클릭 이벤트
            moveToHomeFrag()
        }
        binding.ibAccessGallery.setOnClickListener {    // ImageView 클릭시 갤러리 앱으로 이동
            openGalleryActivityResultLauncher()
        }
    }

    /**
     * 상품 상세정보 인터페이스 URL 접근
     * 정보 데이터를 UI에 바인딩
     */
    private fun setProductData() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(ProductService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<ProductResponseModel> = productService.getProduct(ITM_ID)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as ProductResponseModel
//                Log.e(tag, resBody.toString())
                val product = resBody.data[0]
                binding.product = product

                Glide.with(mContext)
                    .load(product.ITM_IMG1)
                    .placeholder(R.drawable.ic_baseline_add_36)     // 기본이미지
                    .error(R.drawable.ic_baseline_add_36)           // 에러시 대체 이미지
                    .into(binding.ibAccessGallery)
            }

            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * Gallery에 접근하기 위한 ActivityResultLauncher 선언 및 리턴 메서드
     * Gallery에 접근한 이후 작업은 if (result.resultCode == Activity.RESULT_OK) 내부에 선언
     */
    private fun getGalleryActivityResultLauncher() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                data?.let { data ->
                    val uri = data.data
                    Glide.with(mContext)
                        .load(uri)
                        .placeholder(R.drawable.ic_baseline_add_36)
                        .error(R.drawable.ic_baseline_add_36)        //
                        .into(binding.ibAccessGallery)
                }
            }
        }
    }

    /**
     * 선언된 gallery 접근 런처를 실행시키는 메서드
     */
    private fun openGalleryActivityResultLauncher() {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.setType("image/*")
            val mimeTypes = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        galleryLauncher.launch(intent)
    }

    /**
     *  상위 액티비티의 FragmentManager를 이용해
     *  fragment를 replace하기 위한 메서드
     */
    private fun moveToHomeFrag() {
        val parentActivity = activity as ProductActivity
        parentActivity.changeFragment("home", null)
    }

    /**
     * Fragment를 Activity에 attach할 떄 호출
     * Fragment 최초 생성시 호출 (생명주기 최초)
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        // backButton 클릭 이벤트
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToHomeFrag()
            }
        }
        // Activity에 custom backButtonCallback 추가
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallback)
    }

    /**
     * Fragment와 Activity의 연결고리가 끊길 떄 호출
     * Fragment 소멸시 호출 (생명주기 최후)
     */
    override fun onDetach() {
        super.onDetach()
        backButtonCallback.remove()
    }

    companion object {
        @Volatile private var instance: UpdateFragment? = null

        @JvmStatic fun getInstance(): UpdateFragment =
            instance ?: synchronized(this) {
                instance ?: UpdateFragment().also {
                    instance = it
                }
            }
    }
}