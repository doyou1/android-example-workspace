package com.example.roomdbsampling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbsampling.databinding.RvItemHistoryBinding
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.viewmodel.HistoryViewModel

class HistoryRVAdapter(list: List<History>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as HistoryViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class HistoryViewHolder(private val binding: RvItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: History) {

            val viewModel = HistoryViewModel(
                item.id.toString(),
                item.type.toString(),
                item.date,
                item.assetName,
                item.categoryName,
                item.amount.toString(),
                item.memo
            )
            binding.model = viewModel

        }
    }
}