package com.example.qnaproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qnaproject.R

/**
 *  ProductActivity 다섯번째 화면
 */
class Fragment5 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_5, container, false)
    }

    companion object {
        @Volatile private var instance: Fragment5? = null

        @JvmStatic fun getInstance(): Fragment5 =
            instance ?: synchronized(this) {
                instance ?: Fragment5().also {
                    instance = it
                }
            }
    }
}