package com.example.qnaproject.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.databinding.FragmentHomeBinding
import com.example.qnaproject.domain.Product

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    private var productList = arrayListOf<Product>(
        Product(80,
            "https://api.jamjami.co.kr/images/good/N-01-220106160154.png",
        "젤네일 3가지 특가",
        "20,000",
            0,
        "네일 3가지",
            20000),
        Product(80,
            "https://api.jamjami.co.kr/images/good/N-01-220106160154.png",
            "젤네일 3가지 특가",
            "20,000",
            0,
            "네일 3가지",
            20000),
        Product(80,
            "https://api.jamjami.co.kr/images/good/N-01-220106160154.png",
            "젤네일 3가지 특가",
            "20,000",
            0,
            "네일 3가지",
            20000),
        Product(80,
            "https://api.jamjami.co.kr/images/good/N-01-220106160154.png",
            "젤네일 3가지 특가",
            "20,000",
            0,
            "네일 3가지",
            20000),
    )
    private var productAdapter = ProductAdapter(productList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView() {
        binding.rvProduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = productAdapter
    }

    // Java의 static과 비슷, 추후 공부 필요s
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