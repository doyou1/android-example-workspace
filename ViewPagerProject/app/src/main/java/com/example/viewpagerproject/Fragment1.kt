package com.example.viewpagerproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpagerproject.databinding.Fragment1Binding
import com.example.viewpagerproject.databinding.FragmentPageItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class Fragment1 : Fragment() {
    private lateinit var binding:Fragment1Binding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PageApater


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        adapter = PageApater(this)
        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "position: $position"
        }.attach()

    }
}

class PageApater(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment  = when(position) {
            0 -> PageFragment()
            1 -> PageFragment()
            else -> PageFragment()
        }
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment

    }
}

private const val ARG_OBJECT = "object"

class PageFragment : Fragment() {

    private lateinit var binding:FragmentPageItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            binding.text.text = getInt(ARG_OBJECT).toString()
        }
    }
}