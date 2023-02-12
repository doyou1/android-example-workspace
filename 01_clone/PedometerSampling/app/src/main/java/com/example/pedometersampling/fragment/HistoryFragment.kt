package com.example.pedometersampling.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometersampling.databinding.FragmentHistoryBinding
import com.example.pedometersampling.fragment.recyclerview.HistoryAdapter
import com.example.pedometersampling.room.DBHelper
import com.example.pedometersampling.room.Pedometer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun updateCurrentSteps(item: Pedometer?) {
        activity?.let { context ->
            lifecycleScope.launch(Dispatchers.IO) {
                val list = DBHelper.getAll(context)
                Log.d(TAG, "pedometer: $item")
                lifecycleScope.launch(Dispatchers.Main) {
                    updateUI(list)
                }
            }
        }
    }

    private fun updateUI(list: List<Pedometer>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
        binding.recyclerView.adapter = HistoryAdapter(list, context)
    }

    companion object {
        private var instance: HistoryFragment? = null
        fun getInstance(): HistoryFragment {
            if (instance == null) {
                instance = HistoryFragment()
            }
            return instance!!
        }
    }
}