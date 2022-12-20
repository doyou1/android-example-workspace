package com.example.roomdbsampling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbsampling.databinding.RvItemAssetBinding
import com.example.roomdbsampling.databinding.RvItemCategoryBinding
import com.example.roomdbsampling.databinding.RvItemHistoryBinding
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History
import com.example.roomdbsampling.viewmodel.AssetViewModel
import com.example.roomdbsampling.viewmodel.CategoryViewModel
import com.example.roomdbsampling.viewmodel.HistoryViewModel

class CategoryRVAdapter(list: List<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as CategoryViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class CategoryViewHolder(private val binding: RvItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {

            val viewModel = CategoryViewModel(
                item.id.toString(),
                item.type.toString(),
                item.name,
                item.memo
            )
            binding.model = viewModel

        }
    }
}