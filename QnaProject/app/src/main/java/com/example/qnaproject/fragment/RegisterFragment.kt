package com.example.qnaproject.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentRegisterBinding
import retrofit2.http.Url

/**
 * ProductActivity 두번째 화면
 * 상품 등록 화면
 */
class RegisterFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var backButtonCallback: OnBackPressedCallback
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var mContext: Context

//    private val MEM_ID = 94             // 로그인 유저 id
//    private val ITM_ONLY_VIEW = "N"
//    private var PAGE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        // ImageView에 설정돼있는 background 설정 플래그
        // (default 값에는 border가 추가됨, 이미지가 추가되면 border를 지워야함)
        galleryLauncher = getGalleryActivityResultLauncher()
        setClickEvent()

        return binding.root
    }


    /**
     * 레이아웃 아이템 클릭 이벤트
     */
    private fun setClickEvent() {
        binding.toolbarProductRegister.tbBack.setOnClickListener {  // 툴바 '<'(BackButton) 클릭 이벤트
            moveToHomeFrag()
        }
        binding.ibAccessGallery.setOnClickListener {
            openGalleryActivityResultLauncher()
        }
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
        // backButton 클릭 이벤트
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToHomeFrag()
            }
        }
        mContext = context
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
        @Volatile private var instance: RegisterFragment? = null

        @JvmStatic fun getInstance(): RegisterFragment =
            instance ?: synchronized(this) {
                instance ?: RegisterFragment().also {
                    instance = it
                }
            }
    }
}