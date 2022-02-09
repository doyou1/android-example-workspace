package com.example.qnaproject.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentRegisterBinding

/**
 * ProductActivity 두번째 화면
 * 상품 등록 화면
 */
class RegisterFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var backButtonCallback: OnBackPressedCallback

//    private val MEM_ID = 94             // 로그인 유저 id
//    private val ITM_ONLY_VIEW = "N"
//    private var PAGE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        setClickEvent()
        return binding.root
    }

    /**
     * Fragment를 Activity에 attach할 떄 호출
     * Fragment 최초 생성시 호출 (생명주기 최초)
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToHomeFrag()
            }
        }

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
    /**
     * 레이아웃 아이템 클릭 이벤트
     */
    private fun setClickEvent() {
        binding.toolbarProductRegister.tbBack.setOnClickListener {  // 툴바 '<'(BackButton) 클릭 이벤트
            moveToHomeFrag()
        }
    }

    private fun moveToHomeFrag() {
        val parentActivity = activity as ProductActivity
        parentActivity.changeFragment("home")
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