package com.example.fragmentbackstackproject

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbackstackproject.databinding.FragmentAddBinding

class AddFragment(private val color: String, private val count: Int) : Fragment() {

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        when (color) {
            TEXT_RED -> {
                // red FF0000
                binding.layoutWrap.setBackgroundColor(Color.RED)
                binding.tvText.setTextColor(Color.BLACK)
            }
            TEXT_GREEN -> {
                // green 00FF00
                binding.layoutWrap.setBackgroundColor(Color.GREEN)
                binding.tvText.setTextColor(Color.BLACK)
            }
            TEXT_BLUE -> {
                // blue 0000FF
                binding.layoutWrap.setBackgroundColor(Color.BLUE)
                binding.tvText.setTextColor(Color.BLACK)
            }
            TEXT_BLACK -> {
                // black 000000
                binding.layoutWrap.setBackgroundColor(Color.BLACK)
                binding.tvText.setTextColor(Color.WHITE)
            }
        }
        binding.tvText.text = "$color - $count"
    }
}