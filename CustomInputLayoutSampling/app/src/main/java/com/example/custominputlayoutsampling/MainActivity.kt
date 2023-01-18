package com.example.custominputlayoutsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableKeyboard()
        setEditTextEvent()
    }

    private fun disableKeyboard() {
        binding.etInput1.showSoftInputOnFocus = false
        binding.etInput2.showSoftInputOnFocus = false
        binding.etInput3.showSoftInputOnFocus = false
    }

    private fun setEditTextEvent() {
        binding.etInput1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_INPUT_1).show(
                supportFragmentManager,
                "InputFragment"
            )
        }
        binding.etInput2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_INPUT_2).show(
                supportFragmentManager,
                "InputFragment"
            )
        }
        binding.etInput3.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_INPUT_3).show(
                supportFragmentManager,
                "InputFragment"
            )

        }
    }
}