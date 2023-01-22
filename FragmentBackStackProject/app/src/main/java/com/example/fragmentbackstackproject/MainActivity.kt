package com.example.fragmentbackstackproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.fragmentbackstackproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    var count = 0

    private val fragments = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        supportFragmentManager.addOnBackStackChangedListener {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                Log.e(TAG, supportFragmentManager.getBackStackEntryAt(i).name.toString())
            }
            Log.e(TAG, "--------------------------------")
        }

        addFragment(TEXT_RED)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSelectFragment.setOnClickListener {
            val popup = PopupMenu(this, binding.btnSelectFragment)
            popup.setOnMenuItemClickListener { item ->
                binding.btnSelectFragment.text = item.title
                false
            }
            for (item in fragments) {
                popup.menu.add(item)
            }
            popup.show()
        }

        binding.btnPop.setOnClickListener {
            val fragmentId = binding.btnSelectFragment.text.toString()
            if (fragmentId != "Select Fragment") {
                for (item in supportFragmentManager.fragments) {
                    if ((item as AddFragment).id == fragmentId) {
                        supportFragmentManager.popBackStack((item as AddFragment).id, 0)

                        val newList = arrayListOf<String>()
                        for (fragment in fragments) {
                            if ((item as AddFragment).id == fragment) {
                                newList.add(fragment)
                                break
                            } else {
                                newList.add(fragment)
                            }
                        }
                        fragments.clear()
                        fragments.addAll(newList)

                        binding.btnSelectFragment.text = "Select Fragment"
                    }
                }
            }
        }

        binding.btnRed.setOnClickListener {
            addFragment(TEXT_RED)
        }
        binding.btnGreen.setOnClickListener {
            addFragment(TEXT_GREEN)
        }
        binding.btnBlue.setOnClickListener {
            addFragment(TEXT_BLUE)
        }
        binding.btnBlack.setOnClickListener {
            addFragment(TEXT_BLACK)
        }
    }

    private fun addFragment(value: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentId = "$value - $count"
        val fragment = AddFragment(value, count)
        fragmentTransaction.add(R.id.main_frame, fragment)
        fragmentTransaction.addToBackStack(fragmentId)
        fragments.add(fragmentId)
        fragmentTransaction.commit()
        count++
    }
}