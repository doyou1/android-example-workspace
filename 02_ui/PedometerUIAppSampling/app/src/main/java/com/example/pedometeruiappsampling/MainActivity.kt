package com.example.pedometeruiappsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pedometeruiappsampling.databinding.ActivityMainBinding
import com.example.pedometeruiappsampling.fragment.HistoryFragment
import com.example.pedometeruiappsampling.fragment.HomeFragment
import com.example.pedometeruiappsampling.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
//        if (checkActivityPermission(this)) {
            setFragment(HomeFragment.getInstance())
//        }

        binding.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setFragment(HomeFragment.getInstance())
                }
                R.id.history -> {
                    setFragment(HistoryFragment.getInstance())
                }
                R.id.setting -> {
                    setFragment(SettingFragment.getInstance())
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }
}

