package com.example.navigationbackstackproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.navigationbackstackproject.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding:FragmentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)

        setClickEvent()
        return binding.root
    }

    private fun setClickEvent() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_subFragment)
        }
    }

}