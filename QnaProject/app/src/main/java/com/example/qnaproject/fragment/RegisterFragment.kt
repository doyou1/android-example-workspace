package com.example.qnaproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qnaproject.R

/**
 * ProductActivity 두번째 화면
 * 상품 등록 화면
 */
class RegisterFragment : Fragment() {
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
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