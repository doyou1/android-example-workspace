package com.example.qnaproject.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityProductBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.fragment.*
import com.google.android.material.navigation.NavigationBarView

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)

        // Fragment 이동 매니저
        mFragmentManager = supportFragmentManager
        mFragmentManager.beginTransaction().add(binding.mainFrame.id, HomeFragment()).commit()
        setBottomNavigationClickEvent() // bottomNavigation을 통한 fragment 대체

    }

    private fun setBottomNavigationClickEvent() {
        // 바텀 네비게이션 클릭시 fragment 변환
        binding.bnvProduct.setOnItemSelectedListener (
            object: NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId) {
                        R.id.item_product_list -> mFragmentManager.beginTransaction().replace(binding.mainFrame.id, HomeFragment.getInstance()).commit()
                        R.id.item_product_register -> mFragmentManager.beginTransaction().replace(binding.mainFrame.id, RegisterFragment.getInstance()).commit()
                        R.id.item_3 -> mFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment3.getInstance()).commit()
                        R.id.item_4 -> mFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment4.getInstance()).commit()
                        R.id.item_5 -> mFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment5.getInstance()).commit()
                    }
                    return true
                }
            }
        )
    }

}