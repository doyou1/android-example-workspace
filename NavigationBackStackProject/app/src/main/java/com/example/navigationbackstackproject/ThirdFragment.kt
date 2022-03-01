package com.example.navigationbackstackproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.navigationbackstackproject.databinding.FragmentSubBinding
import com.example.navigationbackstackproject.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false)

        setClickEvent()
        return binding.root
    }

    private fun setClickEvent() {
//        binding.btnThird.setOnClickListener {
//            findNavController().navigate(R.id.action_startFragment_to_subFragment)
//        }
    }
}