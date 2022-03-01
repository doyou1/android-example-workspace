package com.example.qnaproject.activity

import android.os.Bundle
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityProductBinding
import com.example.qnaproject.fragment.*
=======
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityProductBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.fragment.*
import com.google.android.material.navigation.NavigationBarView
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff

/**
 * 상품 관련 화면
 * 상품 리스트, 상품 등록 등의 작업
 *
 * 바텀네비게이션 뷰
 */
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

    /**
     * 바텀 네비게이션 아이템 클릭 이벤트 설정
     */
    private fun setBottomNavigationClickEvent() {
        // 바텀 네비게이션 클릭시 fragment 변환
        binding.bnvProduct.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_product_list -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, HomeFragment.getInstance()).commit()
                R.id.item_product_register -> mFragmentManager.beginTransaction()
<<<<<<< HEAD
                    .replace(binding.mainFrame.id, RegisterFragment()).commit()
                R.id.item_3 -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, SwipeFragment()).commit()
                R.id.item_4 -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, FragmentQna.getInstance()).commit()
=======
                    .replace(binding.mainFrame.id, RegisterFragment.getInstance()).commit()
                R.id.item_3 -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, Fragment3.getInstance()).commit()
                R.id.item_4 -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, Fragment4.getInstance()).commit()
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
                R.id.item_5 -> mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, Fragment5.getInstance()).commit()
            }
            true
        }
    }

<<<<<<< HEAD
    /**
     * 하위 프레그먼트에서 프레그먼트 이동, replace 요청을 받는 메서드
     */
    fun changeFragment(flag: String, data: Any?) {

        when(flag) {
            "home" -> { // HomeFragment 이동
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, HomeFragment.getInstance()).commit()
            }
            "home-update-success" -> {
                /**
                 * 상품정보 수정 완료 이후 정보 확인을 위해
                 * 상품 리스트 화면으로 이동시 RecyclerView가 refresh되지 않아
                 * 수정 데이터가 출력되지 않는 문제 발생
                 *
                 * 새로운 HomeFragment 객체를 만드는 것으로 RecyclerView refresh
                 */
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, HomeFragment()).commit()
            }
            "home-register-success" -> {
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, HomeFragment()).commit()
            }
            "register" -> { // 상품 등록화면 이동
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, RegisterFragment()).commit()
            }
            "update" -> {   // 상품 수정화면 이동
                val itmId = data as Int
                UpdateFragment().arguments = Bundle().also { it.putInt("ITM_ID", itmId) }
                mFragmentManager.beginTransaction()
                    .replace(
                        binding.mainFrame.id,
                        /**
                         * 상품리스트에서 특정 아이템 클릭시
                         * 아이템의 ITM_ID 값을 함께 넘겨줌으로써
                         * 상품 상세정보 인터페이스 URL에 접근할 수 있게함
                         */
=======
    fun changeFragment(flag: String, data: Any?) {

        when(flag) {
            "home" -> {
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, HomeFragment.getInstance()).commit()
            }
            "register" -> {
                mFragmentManager.beginTransaction()
                    .replace(binding.mainFrame.id, RegisterFragment.getInstance()).commit()
            }
            "update" -> {
                val itmId = data as Int
                UpdateFragment.getInstance().arguments = Bundle().also { it.putInt("ITM_ID", itmId) }
                mFragmentManager.beginTransaction()
                    .replace(
                        binding.mainFrame.id,
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
                        UpdateFragment.getInstance().also { fragment ->
                            fragment.arguments = Bundle().also { bundle ->
                                bundle.putInt("ITM_ID", itmId)
                            }
                        }).commit()
            }
        }
    }

}