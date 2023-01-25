package com.example.multifragmentswipeeventsampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.multifragmentswipeeventsampling.Util
import com.example.multifragmentswipeeventsampling.databinding.FragmentMonthBinding

class MonthFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentMonthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentDate?.let {  date ->
            binding.tvResult.text = Util.getFullDateText(date)
        }
    }

}