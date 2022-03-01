package com.example.viewpagerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.viewpagerproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(binding.fragmentContainerView.id, Fragment1()).commit()

        setBottomNavigationClickEvent()

    }

    /**
     * 바텀 네비게이션 아이템 클릭 이벤트 설정
     */
    private fun setBottomNavigationClickEvent() {
        // 바텀 네비게이션 클릭시 fragment 변환
        binding.bnvProduct.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_product_list -> supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainerView.id, Fragment1()).commit()
            }
            true
        }
    }
}