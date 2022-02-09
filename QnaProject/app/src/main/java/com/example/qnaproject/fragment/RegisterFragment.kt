package com.example.qnaproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.databinding.FragmentRegisterBinding

/**
 * ProductActivity 두번째 화면
 * 상품 등록 화면
 */
class RegisterFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentRegisterBinding

//    private val MEM_ID = 94             // 로그인 유저 id
//    private val ITM_ONLY_VIEW = "N"
//    private var PAGE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        setClickEvent()

        return binding.root
    }

    private fun setClickEvent() {

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