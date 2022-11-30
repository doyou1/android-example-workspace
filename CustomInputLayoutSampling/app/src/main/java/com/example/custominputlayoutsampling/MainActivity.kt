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
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun setEditTextEvent() {
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_ASSET).show(
                supportFragmentManager,
                "InputFragment"
            )
        }
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_CATEGORY).show(
                supportFragmentManager,
                "InputFragment"
            )
        }
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) InputFragment(binding, FLAG_AMOUNT).show(
                supportFragmentManager,
                "InputFragment"
            )

        }
    }
}