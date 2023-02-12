package com.example.pedometersampling.fragment.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometersampling.databinding.RvItemHistoryBinding
import com.example.pedometersampling.databinding.RvItemStepsBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.room.dto.StepsItem
import com.example.pedometersampling.util.DBUtil
import com.example.pedometersampling.util.Util

class StepsAdapter(private val _list: List<StepsItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: RvItemStepsBinding =
            RvItemStepsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as StepsViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class StepsViewHolder(private val binding: RvItemStepsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StepsItem) {
            binding.model = StepsViewModel(
                item.hour,
                item.steps.toString()
            )
        }
    }
}