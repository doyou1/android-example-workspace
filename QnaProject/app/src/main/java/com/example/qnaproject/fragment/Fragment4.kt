package com.example.qnaproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qnaproject.R

/**
 *  ProductActivity 네번째 화면
 */
class Fragment4 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_4, container, false)
    }

    companion object {
        @Volatile private var instance: Fragment4? = null

        @JvmStatic fun getInstance(): Fragment4 =
            instance ?: synchronized(this) {
                instance ?: Fragment4().also {
                    instance = it
                }
            }
    }
}