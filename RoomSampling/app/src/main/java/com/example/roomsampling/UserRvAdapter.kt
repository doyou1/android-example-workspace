package com.example.roomsampling

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsampling.databinding.RvItemUserBinding

class UserRvAdapter(list: List<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val _list = list

    private val TAG = this::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as ViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ViewHolder(private val binding: RvItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.model = UserViewModel(item.uid.toString(), item.id, item.pwd, item.name)
        }
    }
}