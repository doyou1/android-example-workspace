package com.example.qnaproject.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityProductBinding
import com.google.android.material.navigation.NavigationBarView

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_product)

        supportFragmentManager.beginTransaction().add(binding.mainFrame.id, HomeFragment()).commit()

        binding.bnvProduct.setOnItemSelectedListener (
            object: NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId) {
                        R.id.item_product_list -> supportFragmentManager.beginTransaction().replace(binding.mainFrame.id, HomeFragment()).commit()
                        R.id.item_product_register -> supportFragmentManager.beginTransaction().replace(binding.mainFrame.id, RegisterFragment()).commit()
                        R.id.item_3 -> supportFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment3()).commit()
                        R.id.item_4 -> supportFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment4()).commit()
                        R.id.item_5 -> supportFragmentManager.beginTransaction().replace(binding.mainFrame.id, Fragment5()).commit()
                    }

                    return true
                }
            }
        )

    }

}