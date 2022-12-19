package com.example.roomdbsampling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbsampling.databinding.RvItemAssetBinding
import com.example.roomdbsampling.databinding.RvItemHistoryBinding
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.viewmodel.AssetViewModel
import com.example.roomdbsampling.viewmodel.HistoryViewModel

class AssetRVAdapter(list: List<Asset>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemAssetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as AssetViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class AssetViewHolder(private val binding: RvItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asset) {

            val viewModel = AssetViewModel(
                item.id.toString(),
                item.type.toString(),
                item.name,
                item.amount.toString(),
                item.memo
            )
            binding.model = viewModel

        }
    }
}