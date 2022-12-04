package com.example.viewpagersampling

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewpagersampling.databinding.FragmentMonthBinding
import java.util.*

class MonthFragment(private val yyyy: String, private val mm: String, private val dd: String) : Fragment() {

    private lateinit var binding: FragmentMonthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.tvResult.text = "$yyyy/$mm/$dd"
    }
}