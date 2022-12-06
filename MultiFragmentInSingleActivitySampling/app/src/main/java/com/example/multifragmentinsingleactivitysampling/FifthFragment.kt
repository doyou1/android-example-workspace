package com.example.multifragmentinsingleactivitysampling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.multifragmentinsingleactivitysampling.databinding.FragmentFifthBinding

class FifthFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentFifthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvResult.text = currentDate
    }
}