package com.example.recyclerviewscrollproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewscrollproject.databinding.RvItemBinding

class RvAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as ItemViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItems(items: List<Item>) {
        list.addAll(items)
    }

    inner class ItemViewHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            Log.e(TAG, "$item")

            binding.model = ItemViewModel(
                item.id.toString(),
                item.name,
                item.pwd,
                item.memo
            )
        }
    }
}