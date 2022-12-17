package com.example.transparentviewsampling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.transparentviewsampling.databinding.FragmentTransparentBinding

class TransparentFragment : Fragment() {

    private lateinit var binding: FragmentTransparentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransparentBinding.inflate(inflater, container, false)

        return binding.root
    }
}
